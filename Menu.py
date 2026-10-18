from Shodan import sd
from Whois import ws
from Harvester import th

class Menu:

    def mn(self):
        ferramentas = {
            "whois": ws,
            "shodan": sd,
            "harvester": th
        }

        while True:
            print("Ferramentas disponíveis:", ", ".join(ferramentas), "| sair")
            escolha = input("\nEscreva a ferramenta que você quer usar: ").strip().lower()

            if escolha == "sair":
                break

            elif escolha in ferramentas:
                ferramentas[escolha]()

            else:
                print("Ferramenta não encontrada.")