import whois
import requests

def primeiro(valor):
    if isinstance(valor, (list, tuple)):
        return valor[0] if valor else None
    return valor

def data_iso(valor):
    valor = primeiro(valor)
    return valor.isoformat() if valor else None

def ws():
    dominio = input("\nUsando Whois: ").strip()
    info = whois.whois(dominio)

    print(f"=== Resultados para: {dominio} ===")
    print(f"Registrador (Registrar): {info.registrar}")
    print(f"Data de Criação: {info.creation_date}")
    print(f"Data de Expiração: {info.expiration_date}")
    print(f"Servidores de Nome (DNS): {info.name_servers}")
    print(f"País do Registrante: {info.country}")

    ns = info.name_servers
    if isinstance(ns, str):
        ns = [ns]
    ns = sorted({n.lower() for n in ns}) if ns else []

    payload = {
        "rs": primeiro(info.registrar),
        "cd": data_iso(info.creation_date),
        "ed": data_iso(info.expiration_date),
        "ns": ns,
        "c": primeiro(info.country),
    }

    urlPut = "http://localhost:8080/whois/put"
    urlGet = "http://localhost:8080/whois/get"

    try:
        put = requests.put(urlPut, json=payload, timeout=10)
        print("\n PUT:", put.status_code, put.text)

        get = requests.get(urlGet, timeout=10)
        print("\n GET:", get.text)
    except requests.exceptions.ConnectionError:
        print("\nA API não está rodando na porta 8080.")