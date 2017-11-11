import urllib.request
import xml.etree.ElementTree as ET
from bs4 import BeautifulSoup
import re as RegEx

import json

from time import sleep

class Car:
    def __init__(self, kh10t, tk10t, vk10t, name, baujahre, ps, cm3, kraftstoff, hsntsn):
        self.kh10t = kh10t
        self.tk10t = tk10t
        self.vk10t = vk10t
        self.name = name
        self.baujahre = baujahre
        self.ps = ps
        self.cm3 = cm3
        self.kraftstoff = kraftstoff
        self.hsntsn = hsntsn

    def __str__(self):
        return "{ kh10t: " + self.kh10t + \
        " tk10t: " + self.tk10t + \
        " vk10t: " + self.vk10t + \
        " name: " + self.name + \
        " baujahre: " + self.baujahre + \
        " ps: " + self.ps + \
        " cm3: " + self.cm3 + \
        " kraftstoff: " + self.kraftstoff + \
        "hsntsn: " + self.hsntsn + " }"


url = "http://www.autoampel.de/typklassen/liste/"
cars = []

def calcSleep(url = "", sleepTime = 0):
    repetitions = 100

    for i in range(0, repetitions):
        print("i: %d" % (i))
        sleep(sleepTime)
        try:
            urllib.request.urlopen(url).read()
        except urllib.error.HTTPError as ex:
            print(ex)
            print("sleeptime of %f not enough" % (sleepTime))
            sleepTime = calcSleep(url, sleepTime + .05)
            break

    return sleepTime

def obj_dict(obj):
    return obj.__dict__

i = 1
bedingung = True
while(bedingung):
    print("i: %d" %(i))
    try:
        response = urllib.request.urlopen(url + str(i))
        html = response.read()
        soup = BeautifulSoup(html, 'html.parser')

        table = soup.find_all("table", { "class" : "autolist" })[0].tbody

        for row in table.find_all("tr"):
            cells = row.find_all("td")

            if len(cells) is not 9:
                continue

            kh10t = str(cells[0].contents)
            tk10t = str(cells[1].contents)
            vk10t = str(cells[2].contents)
            name = str(cells[3].a.contents)
            baujahre = str(cells[4].contents)
            ps = str(cells[5].contents)
            cm3 = str(cells[6].contents)
            kraftstoff = str(cells[7].contents)
            hsntsn = str(cells[8].contents)

            car = Car(kh10t, tk10t, vk10t, name, baujahre, ps, cm3, kraftstoff, hsntsn)
            cars.append(car)

        i += 1
    except urllib.error.HTTPError as ex:
        print(ex)
        if( "404" in str(ex) ):
            print("Seite %d existiert nicht mehr" % (i))
            bedingung = False
        else:
            sleep(4.5)
    sleep(4.5)

with open("ca.rs", "w") as fp:
    fp.write(json.dumps(cars, default=obj_dict))
