#!/usr/bin/env python3
# small command line tool for formatting info about a link into JSON

import json

links_file = open("/Users/sherryuan/Documents/projects/adherenceapp/app/src/main/java/com/squad/betakuma/adherence_app/ocr/medication.json", "r")
links = json.load(links_file)
links_file.close()

done = False
while not done:
	genericName = input("Enter generic name:\n")
	brandName = input("Enter brandName:\n")
	dose = input("Enter dose:\n")
	description = input("Enter description:\n")
	quantity = input("Enter quantity:\n")
	totalQuantity = input("Enter totalQuantity:\n")
	refills = input("Enter refills:\n")
	totalRefills = input("Enter refills:\n")
	DIN = input("Enter DIN:\n")
	links.append({
		brandName: {
        'quantity': quantity,
        'totalQuantity': totalQuantity,
        'refills': refills,
        'totalRefills': refills,
        'medication': { 
        	'DIN': DIN, 
        	'genericName': genericName,
        	'dose': dose,
        	'description': description,
        	'sideEffects': []
        },
        'surveyResponses': []
        }
    })

	done_str = ""
	while done_str != 'y' and done_str != 'n':
		done_str = input("Done? (y/n)")
	if done_str == 'y':
		done = True

links_file = open("/Users/sherryuan/Documents/projects/adherenceapp/app/src/main/java/com/squad/betakuma/adherence_app/ocr/medication.json", "w")
json.dump(links, links_file, indent=4, separators=(',', ': '))
