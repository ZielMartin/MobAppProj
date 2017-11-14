import json
import re as RegEx

class Cars:
    def __init__(self):
        self.cars = []

    def append(self, car):
        self.cars.append(car)

class Car:
    def __init__(self, j):
        self.__dict__ = j

    def CLEANME(self):
        pattern = RegEx.compile("\['(.*)'\]")
        for eintrag in self.__dict__:
            result = pattern.match(self.__dict__[eintrag])
            if result:
                self.__dict__[eintrag] = result.group(1)

        result = RegEx.match("(.*)\s?/\s?(.*)", self.__dict__["hsntsn"])
        self.hsn = result.group(1)
        self.tsn = result.group(2)

        del self.__dict__["hsntsn"]
        del self.__dict__["kh10t"]
        del self.__dict__["tk10t"]
        del self.__dict__["vk10t"]

def obj_dict(obj):
    return obj.__dict__

with open("ca.rs", "r") as inp:
    with open("out.json", "w") as out:
        cars = Cars()
        for x in json.loads(inp.read()):
            car = Car(x)
            car.CLEANME()
            cars.append(car)
        json.dump(cars, out, default=obj_dict)
