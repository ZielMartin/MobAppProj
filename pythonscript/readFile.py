import json

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



with open("ca.rs", "r") as fp:
    for x in json.loads(fp.read()):
        car = Car(x["kh10t"], x["tk10t"], x["vk10t"], x["name"], x["baujahre"], x["ps"], x["cm3"], x["kraftstoff"], x["hsntsn"])
        print(car)
        input()
