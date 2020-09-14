#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void send_data(unsigned char *data, int len) {
  for (int i=0; i < len; i++) {
    printf("%2x ", data[i]);
  }
  printf("\n");
}

int main() {
  unsigned char *p;
  unsigned char data[8] = {0};
  p = data;
  printf("p = %p\n", p);
  send_data(data, 16);
  return 0;
}
