#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
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
    struct sockaddr_in server_addr;
    socklen_t len = sizeof(struct sockaddr_in);
    char buffer[BUFSIZ];

    if(argc != 3)
    {
        fprintf(stderr, "Usage: ./client <ip> <port>");
        exit(EXIT_FAILURE);
    }

    printf("Trying to connect to server %s on port %s\n\n", argv[1], argv[2]);

    memset(&server_addr, 0, len);
    server_addr.sin_family = AF_INET;
    inet_pton(AF_INET, argv[1], &server_addr.sin_addr);
    server_addr.sin_port = htons(atoi(argv[2]));

    if((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) handle_error("socket");
    if((connect(sockfd, (struct sockaddr*)&server_addr, len)) < 0) handle_error("connect");
    printf("[+]Connection with server %s to port %d\n\n", inet_ntoa(server_addr.sin_addr), ntohs(server_addr.sin_port));

    while(true)
    {
        printf("Enter a film: ");
        fgets(buffer, BUFSIZ, stdin);

        if (strcasecmp(buffer, "stop\n") == 0)
        {
            printf("\nGoodbye!\n");
            break;
        }
        
        if((send(sockfd, buffer, strlen(buffer), 0) < 0)) handle_error("send");
        if((n = recv(sockfd, buffer, BUFSIZ, 0)) < 0) handle_error("recv");
        buffer[n] = '\0';

        printf("Reply -> %s\n\n", buffer);
    }

    return EXIT_SUCCESS;
}