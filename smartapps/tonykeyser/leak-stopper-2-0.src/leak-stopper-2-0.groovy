/**
 *  Leak Stopper 2.0
 *
 *  Copyright 2016 Tony Keyser
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Leak Stopper 2.0	",
    namespace: "tonykeyser",
    author: "Tony Keyser",
    description: "Turns multiple switches on and off based on moisture sensor input. A variation of Dry the Wet Spot by Scottin Pollock ",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Developers/dry-the-wet-spot.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Developers/dry-the-wet-spot@2x.png"
)


preferences {
	section("When water is sensed...") {
		input "sensor", "capability.waterSensor", title: "Which moisture sensor?", required: true
	}
	section("Turn on/off switches...") {
        input "power", "capability.switch", title: "Turn on/off...", multiple: true
	}
}

def installed() {
	subscribe(sensor, "water.dry", waterHandler)
	subscribe(sensor, "water.wet", waterHandler)
}

def updated() {
	unsubscribe()
	subscribe(sensor, "water.dry", waterHandler)
	subscribe(sensor, "water.wet", waterHandler)
}

def waterHandler(evt)  
{
	log.debug "Moisture Sensor is reading ${evt.value}"
	if (evt.value == "wet") {
		power.off()
        log.debug "${app.label} - ${power.label} are Off"
	} else if (evt.value == "dry") {
		power.on([delay:3000])
        log.debug "${app.label} - ${power.label} are On"	
    }
}