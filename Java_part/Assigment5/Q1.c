#include <stdio.h>
#include <malloc.h>

/*1) Լɪ�����⣺��֪n���ˣ��Ա��1��2��3...n�ֱ��ʾ��Χ����һ��Բ����Χ��
 * �ӱ��Ϊk���˿�ʼ����������m���Ǹ��˳��У�������һ�����ִ�1��ʼ����������m���Ǹ����ֳ��У�
 * ���˹����ظ���ȥ��ֱ��Բ����Χ����ֻʣ��һ���ˡ�����������Ȧ�����ţ�Ҫ������������⣩��
 *
 * ��������:
 * С����������n����ʼ��k����Ȧ��m
 *
 * �������:
 * ���������Ȧ��С���ѵ���ţ��Լ���������Ȧ�ڵ�С������š�
 * */
typedef struct node {
    int number;
    struct node *next;
} node;

int main(int argc, char **argv) {
    int n, k, m;
    printf("������n\n");
    scanf("%d", &n);
    printf("��ʼ��k\n");
    scanf("%d", &k);
    k %= n;
    printf("��Ȧ��m\n");
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
    printf("���ʤ����%d", t->number);
    return 0;
}
