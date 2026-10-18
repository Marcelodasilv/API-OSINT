import requests
import shodan

def sd():

    scanShodan = (input("Usando Shodan: "))

    chaveShodan = shodan.Shodan(key="SUA_CHAVE")
    info_ip = chaveShodan.host(scanShodan)

    print(f"Organização: {info_ip.get('org')}")
    print(f"País: {info_ip.get('country_name')}")
    print(f"Portas abertas: {info_ip.get('ports')}")

    #PUT
    urlPut = "http://localhost:8080/shodan/put"
    payload = {
        "org": info_ip.get('org'),
        "ps": info_ip.get('country_name'),
        "pr": info_ip.get('ports')
    }
    response = requests.put(urlPut, json=payload)
    print("\n PUT:", response.status_code, response.text)

    #GET
    urlGet = "http://localhost:8080/shodan/get"
    response = requests.get(urlGet)
    print("\n GET:", response.text)