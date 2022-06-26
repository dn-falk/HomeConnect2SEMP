package de.dennisfalk.homeconnect2semp.model.semp;

import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeAppliance;
import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeConnect;
import de.dennisfalk.homeconnect2semp.model.semp.sempMessage.*;
import de.dennisfalk.homeconnect2semp.repository.HomeApplianceRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * Semp manager. This class process all relevant semp data
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Slf4j
@Entity
public class SEMPManager {

    @Id
    String name;

    //SSDP SEMP UUID
    String uuid;

    //HomeConnect2SEMP Server IP
    String localServerIpAddress;

    //SEMP Device ID vendor part
    String sempDeviceIDVendor;

    boolean activateSempConnection;

    /**
     * Constructor
     * @param name name of SEMPManager entity
     */
    public SEMPManager(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
        this.sempDeviceIDVendor = "F-29101985";
        this.activateSempConnection = false;

    }

    /**
     * MEthod for processing the received message from the energymanager
     * @param em2Device the message as em2Device object
     * @param homeConnect home connect instance
     * @param homeApplianceRepository home appliance repository instance
     */
    public void computeEMmessage(EM2Device em2Device, HomeConnect homeConnect, HomeApplianceRepository homeApplianceRepository){

        for(DeviceControlType device: em2Device.getDeviceControl()){
            HomeAppliance homeAppliance = homeApplianceRepository.findBySempID(device.getDeviceId());
            if(homeAppliance != null){
                if(device.isOn() && homeAppliance.isEnergymanagerStartable()){
                    homeConnect.turnHomeApplianceOn(homeAppliance.getHaId());
               }
            }
        }
    }

    /**
     * Method for refreshing the description xml file
     * @param resource Resource of the xml template
     * @return new xml description string
     */
    public String refreshDescriptionSEMPxmlFile(Resource resource) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            Document input = factory
                    .newDocumentBuilder()
                    .parse(resource.getInputStream());
            //updates the server ip
            NodeList nodes = input.getElementsByTagName("semp:server");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element value = (Element) nodes.item(i);
                value.setTextContent("http://"+localServerIpAddress+":8080");
            }
            //updates the uuid
            NodeList nodesUUID = input.getElementsByTagName("UDN");
            for (int i = 0; i < nodesUUID.getLength(); i++) {
                Element value = (Element) nodesUUID.item(i);
                value.setTextContent("uuid:"+uuid);
            }
            //updates the serial number (uuid)
            NodeList nodesSN = input.getElementsByTagName("serialNumber");
            for (int i = 0; i < nodesSN.getLength(); i++) {
                Element value = (Element) nodesSN.item(i);
                value.setTextContent(uuid);
            }
            //produce the xml string
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer xformer = transformerFactory.newTransformer();
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Writer output = new StringWriter();
            xformer.transform(new DOMSource(input), new StreamResult(output));

            return output.toString();

        }catch(Exception e){
            log.error(String.valueOf(e));
        }
        return null;
    }

    /**
     * Method for generating the xml with all home appliance devices
     * @param homeAppliances List of all home appliances
     * @return object Device2EM
     */
    public Device2EM generateSEMPdeviceInfo(List<HomeAppliance> homeAppliances){

        Device2EM device2EM = new Device2EM();

        for(HomeAppliance homeAppliance : homeAppliances) {

            //Device Info
            DeviceInfoType deviceInfoType = new DeviceInfoType();

            //Identification
            IdentificationType identificationType = new IdentificationType();

            identificationType.setDeviceId(homeAppliance.getSempID());
            identificationType.setDeviceName(homeAppliance.getName());
            identificationType.setDeviceSerial(homeAppliance.getHaId());
            identificationType.setDeviceVendor(homeAppliance.getBrand());

            //Check HomeAppliance Type
            switch(homeAppliance.getType()){
                case "Dishwasher":
                    identificationType.setDeviceType("DishWasher");
                    break;
                case "Dryer":
                    identificationType.setDeviceType("Dryer");
                    break;
                case "Washer":
                    identificationType.setDeviceType("WashingMachine");
                    break;
                default:
                    identificationType.setDeviceType("Other");
            }
            deviceInfoType.setIdentification(identificationType);

            //Characteristics
            CharacteristicsType characteristicsType = new CharacteristicsType();
            characteristicsType.setMaxPowerConsumption(homeAppliance.getConnectionPowerInWatt());
            deviceInfoType.setCharacteristics(characteristicsType);

            //Capabilities
            CapabilitiesType capabilitiesType = new CapabilitiesType();

            CapPowerMeasurementType capPowerMeasurementType = new CapPowerMeasurementType();
            capPowerMeasurementType.setMethod("Estimation");
            capabilitiesType.setCurrentPower(capPowerMeasurementType);

            CapTimestampType capTimestampType = new CapTimestampType();
            capTimestampType.setAbsoluteTimestamps(false);
            capabilitiesType.setTimestamps(capTimestampType);

            CapInterruptionsType capInterruptionsType = new CapInterruptionsType();
            capInterruptionsType.setInterruptionsAllowed(false);
            capabilitiesType.setInterruptions(capInterruptionsType);

            CapRequestsType capRequestsType = new CapRequestsType();
            capRequestsType.setOptionalEnergy(false);
            capabilitiesType.setRequests(capRequestsType);
            deviceInfoType.setCapabilities(capabilitiesType);

            device2EM.getDeviceInfo().add(deviceInfoType);

            //DeviceStatus
            DeviceStatusType deviceStatusType = new DeviceStatusType();

            //Device ID
            deviceStatusType.setDeviceId(homeAppliance.getSempID());
            //TODO: EM Signal checken
            deviceStatusType.setEMSignalsAccepted(homeAppliance.isEnergymanagerStartable());

            //Is device running?
            boolean isDeviceRunning = false;
            if(homeAppliance.isConnected()) {

                if (homeAppliance.getBshCommonStatusOperationState().equals("BSH.Common.EnumType.OperationState.Run")){
                    isDeviceRunning = true;
                }

                if(isDeviceRunning) {
                    deviceStatusType.setStatus("On");
                }else{
                    deviceStatusType.setStatus("Off");
                }
            }else {
                deviceStatusType.setStatus("Offline");
            }
            //Power Consumption
            PowerConsumptionType powerConsumptionType = new PowerConsumptionType();
            PowerInfoType powerInfoType = new PowerInfoType();

            if(isDeviceRunning) {
                powerInfoType.setAveragePower(homeAppliance.getAverageOperatingPowerPerMinuteInWatt());
            }else{
                powerInfoType.setAveragePower(0);
            }
            powerInfoType.setTimestamp(0);
            powerInfoType.setAveragingInterval(60);

            powerConsumptionType.getPowerInfo().add(powerInfoType);
            deviceStatusType.setPowerConsumption(powerConsumptionType);

            device2EM.getDeviceStatus().add(deviceStatusType);

            //PlanningRequest
            PlanningRequestType planningRequestType = new PlanningRequestType();

            TimeframeType timeframeType = new TimeframeType();
            timeframeType.setDeviceId(homeAppliance.getSempID());

            //Get Programm Options
            int earliestStart = 0;
            int latestEnd;

            int relativeStartTime = homeAppliance.getBshCommonOptionStartInRelative();
            int maxRunningTime = homeAppliance.getBshCommonOptionRemainingProgramTime();

            latestEnd = relativeStartTime + maxRunningTime;

            timeframeType.setEarliestStart(earliestStart);
            timeframeType.setLatestEnd(latestEnd);
            timeframeType.setMaxRunningTime(maxRunningTime);
            timeframeType.setMinRunningTime(maxRunningTime);

            planningRequestType.getTimeframe().add(timeframeType);
            device2EM.getPlanningRequest().add(planningRequestType);

        }
        return device2EM;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SEMPManager that = (SEMPManager) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return 1244268211;
    }
}
