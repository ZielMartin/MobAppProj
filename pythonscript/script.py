from lxml.etree import fromstring, tostring
import json, xmljson
import urllib.request
from io import StringIO


def buildXML(url):
    xml_string = urllib.request.urlopen(url).read()
    xml = fromstring(xml_string)
    return xml


data = {}

url1 = "http://services.mobile.de/1.0.0/refdata/sites/GERMANY/classes/Car/makes"

marken = buildXML(url1)

for marke in marken:
    data[str(marke.attrib.get("key"))] = []
    url2 = marke.attrib.get("url") + "/models"
    try:
        models = buildXML(url2)
    except Exception as err:
        pass

    for model in models:
        data[str(marke.attrib.get("key"))].append(str(model.attrib.get("key")))

print(json.dumps(data))
