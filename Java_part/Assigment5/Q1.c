#include <stdio.h>
#include <malloc.h>

/*1) 约瑟夫环问题：已知n个人（以编号1，2，3...n分别表示）围坐在一张圆桌周围。
 * 从编号为k的人开始报数，数到m的那个人出列；他的下一个人又从1开始报数，数到m的那个人又出列；
 * 依此规律重复下去，直到圆桌周围的人只剩下一个人。输出这个人在圈里的序号（要求用链表来求解）。
 *
 * 输入描述:
 * 小朋友总人数n，起始点k，出圈数m
 *
 * 输出描述:
 * 依次输出出圈的小朋友的序号，以及最终留在圈内的小朋友序号。
 * */
typedef struct node {
    int number;
    struct node *next;
} node;

int main(int argc, char **argv) {
    int n, k, m;
    printf("总人数n\n");
    scanf("%d", &n);
    printf("起始点k\n");
    scanf("%d", &k);
    k %= n;
    printf("出圈数m\n");
    scanf("%d", &m);
    node *head = malloc(sizeof(node));
    head->number = 1;
    node *last = head;
    for (int i = 2; i <= n; ++i) {
        node *a = malloc(sizeof(node));
        a->number = i;
        last->next = a;
        last = a;
    }
    last->next = head;
    node *t = head;
    while (k > 1) {
        t = t->next;
        k--;
    }
    while (t->next != t) {
        for (int i = 1; i < m; ++i) {
            last = t;
            t = t->next;
        }
        last->next = t->next;
        printf("%d\n", t->number);
        free(t);
        t = last->next;
    }
    printf("最后胜利者%d", t->number);
    return 0;
}
