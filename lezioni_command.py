import requests

def download_orario_lezioni_dmi():
    """
    Scarica l'orario delle lezioni del DMI UNICT dal sito web ufficiale.

    Ritorna il percorso del file pdf scaricato.
    """

    # Connetti al sito web
    response = requests.get("https://web.dmi.unict.it/sites/default/files/Orario%202023-24_v12.pdf")

    # Scarica il pdf
    filename = "orario_lezioni_dmi_2023_2024.pdf"
    with open(filename, "wb") as f:
        f.write(response.content)

    # Ritorna il percorso del file pdf scaricato
    return filename


def send_pdf_to_telegram(update, context, filename):
    """
    Invia il file pdf all'utente tramite Telegram Bot.

    Args:
        update: update event
        context: context passed by the handler
        filename: percorso del file pdf da inviare
    """

    # Elimina la tastiera dalla chat dell'utente
    context.bot.deleteMessage(chat_id=update.effective_chat.id, message_id=update.message.message_id)

    # Invia il file pdf direttamente alla chat
    context.bot.sendDocument(chat_id=update.effective_chat.id, document=open(filename, "rb"))

    # Invia response.content alla chat dell'utente
    context.bot.sendMessage(
        chat_id=update.effective_chat.id,
        text=f"Ecco il pdf dell'orario delle lezioni del DMI UNICT: \n\n{response.content}",
    )


def main():
    download_orario_lezioni_dmi()

if __name__ == "__main__":
    main()
