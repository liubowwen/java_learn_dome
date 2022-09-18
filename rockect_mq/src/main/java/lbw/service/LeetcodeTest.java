package lbw.service;

import java.util.*;

public class LeetcodeTest {
    public static void main(String[] args) {
        LeetcodeTest l=new LeetcodeTest();
        try {
            l.test();
        } catch (BusinessErrorException e) {
            e.printStackTrace();
        }
    }
    public void test() throws BusinessErrorException {
        try {
            LeetcodeTest leetcodeTest=null;
            leetcodeTest.test();
        } catch (Exception e) {
            throw new BusinessErrorException("运行错误");
        }

    }


    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode head1 = head;
        ListNode head2 = head;
        ListNode lastNode = new ListNode();
        int i = 0;
        if (head.next==null&&n==1){
            return null;
        }
        while (head1 != null) {
            head1 = head1.next;
            if (i == n) {
                head2 = head2.next;
            } else {
                i++;
            }
            if (head1 == null) {
                lastNode.next = head2.next;
            }
            lastNode = head2;
        }
        if (head2==head){
            return head.next;
        }

        return head;
    }

    public static boolean isisValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put(')', '(');
        map.put('[', ']');
        map.put(']', '[');
        map.put('{', '}');
        map.put('}', '{');
        Stack<Character> characterStack = new Stack<>();
        Stack<Character> valid = new Stack<>();
        for (char c : s.toCharArray()) {
            characterStack.add(c);
        }
        while (!characterStack.isEmpty()) {
            Character character = characterStack.pop();
            if (valid.isEmpty()) {
                valid.add(character);
            } else {
                Character peek = valid.peek();
                if (peek.equals(character)) {
                    valid.add(character);
                } else {
                    valid.pop();
                }
            }
        }
        return valid.isEmpty();
    }

    public static void backtracking(Integer left, Integer right, Set<String> set, String a, int n) {

        if (left > 0) {
            a += "(";
            left--;
            backtracking(left, right, set, a, n);
        }
        if (right > 0) {
            a += ")";
            right--;
            backtracking(left, right, set, a, n);
        }
        if (a.length() == n * 2) {

            set.add(a);

        }

    }
}
