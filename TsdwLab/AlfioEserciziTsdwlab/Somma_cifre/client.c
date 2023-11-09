#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <errno.h>
#include <arpa/inet.h>
#include <unistd.h>

#define handle(msg)                               \
    do                                            \
    {                                             \
        fprintf(stderr, "[%d] Error ", __LINE__); \
        perror(msg);                              \
        exit(EXIT_FAILURE);                       \
    } while (false);

int main(int argc, char **argv)
{
    int sockfd, n;
    struct sockaddr_in server_addr;
    socklen_t len = sizeof(struct sockaddr_in);
    char sendline[BUFSIZ], recvline[BUFSIZ];

    // Check command-line arguments 
    if (argc != 3)
    {
        printf("Error: Usage: ./client <ip> <port>\n");
        exit(EXIT_FAILURE);
    }

    // Initialize server address 
    memset(&server_addr, 0, len);
    server_addr.sin_family = AF_INET;
    inet_pton(AF_INET, argv[1], &server_addr.sin_addr);
    server_addr.sin_port = htons(atoi(argv[2]));

    if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) handle("socket\n");

    // Connect to the server
    if ((connect(sockfd, (struct sockaddr *)&server_addr, len)) < 0) handle("connect\n");
    printf("[+]Connected to server %s on port %d\n\n", inet_ntoa(server_addr.sin_addr), ntohs(server_addr.sin_port));

    while (true)
    {   
        // clear buffers
        bzero(sendline, BUFSIZ);
        bzero(recvline, BUFSIZ);

        printf("Enter a message: ");
        fgets(sendline, BUFSIZ, stdin); // Transfer the input message into the "sendline" buffer, including the '\n' character.
        // IMPORTANT: don't remove '/n' character !!!

        if (strcasecmp(sendline, "stop\n") == 0)
        {
            printf("\nGoodbye!\n");
            break;
        }

        // Send the message to the server.
        if ((send(sockfd, sendline, strlen(sendline), 0)) < 0) handle("send\n");

        // Receive a reply from the server.
        if ((n = recv(sockfd, recvline, BUFSIZ, 0)) < 0) handle("recv\n");
        recvline[n] = '\0'; 

        // Display the server's reply.
        printf("Server's reply: %s\n", recvline);
    }

    printf("[+]Connection closed...\n");
    close(sockfd);

    return EXIT_SUCCESS;
}
