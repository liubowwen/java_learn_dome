package com.nat.service;


public class Test {

    public static void main(String[] args) {
        int n[] = {6, 5, 2, 7, 3, 9, 8, 4, 10, 1};
        quicksort(n);
        System.out.print("：");
        for (int m : n) {
            System.out.print(m + " ");
        }
    }

    private static void quicksort(int[] n) {
        sort(n, 0, n.length - 1);
    }

    private static void sort(int[] n, int l, int r) {
        if (l < r) {
            int index = p(n, l, r);
            sort(n,0,index-1);
        }
    }

    private static int p(int[] n, int l, int r) {
        int p = n[l];
        while (l < r) {
            while (n[l] < p) {
                l++;
            }
            while (n[r] > p) {
                r--;
            }
            swap(n, l, r);
        }
        return l;
    }

    private static void swap(int[] n, int l, int r) {
        int temp = n[l];
        n[l] = n[r];
        n[r] = temp;

    }


}

