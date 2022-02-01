/*
 * MIT License
 *
 * Copyright (c) 2021 Dennis Nikolas Falk <mail@dennisfalk.de>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.dennisfalk.homeconnect2semp.model.homeConnect;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Home appliance class
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class HomeAppliance {

    /*
        Unique identifier representing a specific home appliance.
         */
    @Id
    @Column(nullable = false, unique = true)
    String haId;

    /*
    User-friendly name of the home appliance (e.g. "My Oven")
     */
    @Column(nullable = false)
    String name;

    /*
    Type of home appliance, e.g. "oven".
     */
    @Column(nullable = false)
    String type;

    /*
    Brand of the home appliance, e.g. "BOSCH"
     */
    @Column(nullable = false)
    String brand;

    /*
    The type code (VIB) of the home appliance.
     */
    @Column(nullable = false)
    String vib;

    /*
    Combination of VIB and customer index (VIB/KI)
     */
    @Column(nullable = false)
    String enumber;

    /*
    Current connection state of the home appliance. True if the home appliance is online, false otherwise.
     */
    @Column(nullable = false)
    boolean connected;

    /*
    Connection power of the home appliance (for example 2200W). Read nameplate for this information
     */
    @JsonIgnore
    @Column()
    int connectionPowerInWatt;

    /*
    Average operating power of the home appliance. Energymanager asked every minute for the average power of the last 60 seconds
     */
    @JsonIgnore
    @Column()
    int averageOperatingPowerPerMinuteInWatt;

    @JsonIgnore
    @Column()
    boolean bshCommonStatusRemoteControlStartAllowed;

    @JsonIgnore
    @Column()
    boolean bshCommonStatusRemoteControlActive;

    @JsonIgnore
    @Column()
    String bshCommonStatusOperationState = "";

    @JsonIgnore
    @Column(unique = true)
    String sempID;

    @JsonIgnore
    boolean energymanagerStartable = false;

    @JsonIgnore
    int bshCommonOptionStartInRelative = 0;

    @JsonIgnore
    int bshCommonOptionRemainingProgramTime = 0;


    /**
     * Method for getting a readable operation state
     * @return String readable operation state
     */
    public String getOperationStateReadable(){
        if (bshCommonStatusOperationState.lastIndexOf('.') != -1){
            return bshCommonStatusOperationState.substring(bshCommonStatusOperationState.lastIndexOf('.')+1);
        } else {
            return bshCommonStatusOperationState;
        }
    }

    /**
     * Method for checking if the home appliance is remote startable
     * @return boolean
     */
    public boolean isSempRemoteStartable(){

        return (bshCommonStatusOperationState.equals("BSH.Common.EnumType.OperationState.DelayedStart")
                || bshCommonStatusOperationState.equals("BSH.Common.EnumType.OperationState.Run"))
                && bshCommonStatusRemoteControlActive
                && bshCommonStatusRemoteControlStartAllowed;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HomeAppliance that = (HomeAppliance) o;

        return Objects.equals(haId, that.haId);
    }

    @Override
    public int hashCode() {
        return 201266389;
    }
}
