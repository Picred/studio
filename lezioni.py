import requests
from bs4 import BeautifulSoup
# import constant = "https://web.dmi.unict.it/it/corsi/l-31/orario-delle-lezioni/" 
# import constant = "<sites/default>" path per la ricerca fra tutti gli "a href"
def get_pdf():
    main_link = "https://web.dmi.unict.it/it/corsi/l-31/orario-delle-lezioni/"
    base_link = "https://web.dmi.unict.it"
    soup = BeautifulSoup(requests.get(main_link).content, "html.parser")

    for tmp in soup.find_all("a"):
        if "sites/default" in str(tmp):
            tmp = tmp.get("href")
            # print(base_link + tmp,"\n")
            full_pdf_link = base_link + tmp
            response = requests.get(full_pdf_link).content #pdf file
            # context.bot.sendDocument(... document = <response>)

            #with open('Orario.pdf', 'wb') as f:
            #   f.write(response)

    print(type(soup.find_all("a")))