#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <errno.h>
#include <arpa/inet.h>
#include <unistd.h>

#define handle_error(msg)                           \
    do{                                             \
        fprintf(stderr, "[%d] error ", __LINE__);   \
        perror(msg);                                \
        exit(EXIT_FAILURE);                         \
    }while (false);

int main(int argc, char* argv[])
{
    int sockfd, n;
    struct sockaddr_in serverAddress;
    socklen_t len = sizeof(struct sockaddr_in);
    char buffer[BUFSIZ];

    if(argc != 3) handle_error("usage : ./client <ip> <port>");

    // Address
    memset(&serverAddress, 0, len);
    serverAddress.sin_family = AF_INET;
    inet_pton(AF_INET, argv[1], &serverAddress.sin_addr);
    serverAddress.sin_port = htons(atoi(argv[2]));

    // Socket
    if((sockfd= socket(AF_INET, SOCK_STREAM, 0)) < 0) handle_error("socket");
    if((connect(sockfd, (struct sockaddr*)&serverAddress, len)) < 0) handle_error("connect");
    printf("[+]Connection with server %s to port %d\n\n", inet_ntoa(serverAddress.sin_addr), ntohs(serverAddress.sin_port));

    while(true)
    {
        printf("Enter an alfanumeric string: ");
        fgets(buffer, BUFSIZ, stdin);

        if (strcasecmp(buffer, "stop\n") == 0)
        {
            printf("\nGoodbye!\n");
            break;
        }

        if((send(sockfd, buffer, strlen(buffer), 0)) < 0) handle_error("send");
        if((n = recv(sockfd, buffer, BUFSIZ, 0)) < 0) handle_error("recv");
        buffer[n] = 0;

        printf("Outcome: %s\n", buffer);
    }
    printf("\n[+]Connection closed..\n");

    return EXIT_SUCCESS;
}