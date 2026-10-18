import json
import os
import subprocess
import tempfile

import requests

def th():
    dominio = input("\nUsando theHarvester: ").strip()

    with tempfile.TemporaryDirectory() as tmp:
        base = os.path.join(tmp, "saida")
        cmd = ["theHarvester", "-d", dominio,
               "-b", "crtsh,hackertarget,rapiddns",
               "-l", "100", "-f", base]
        try:
            subprocess.run(cmd, capture_output=True, text=True, timeout=180)
        except FileNotFoundError:
            print("theHarvester não encontrado. Ele está instalado e no PATH?")
            return
        except subprocess.TimeoutExpired:
            print("A consulta demorou demais (timeout).")
            return

        try:
            with open(base + ".json", encoding="utf-8") as f:
                dados = json.load(f)
        except FileNotFoundError:
            print("Ele não gerou o arquivo de saída. Rode o comando à mão para ver o erro.")
            return

    emails = sorted(set(dados.get("emails", [])))
    hosts = sorted(set(dados.get("hosts", [])))
    ips = sorted(set(dados.get("ips", [])))

    print(f"=== Resultados para: {dominio} ===")
    print(f"E-mails: {emails}")
    print(f"Hosts: {hosts}")
    print(f"IPs: {ips}")

    payload = {"d": dominio, "em": emails, "ho": hosts, "ip": ips}

    urlPut = "http://localhost:8080/harvester/put"
    urlGet = "http://localhost:8080/harvester/get"
    try:
        put = requests.put(urlPut, json=payload, timeout=10)
        print("\n PUT:", put.status_code, put.text)
        get = requests.get(urlGet, timeout=10)
        print("\n GET:", get.text)
    except requests.exceptions.ConnectionError:
        print("\nA API não está rodando na porta 8080.")