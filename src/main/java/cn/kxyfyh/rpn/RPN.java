package cn.kxyfyh.rpn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class RPN {
        ArrayList<String> symbol = new ArrayList(Arrays.asList(new String[]{"+","-","*","/","(",")"}));
        //将字符串中数字和符号拆除来，放进列表中
        public ArrayList<String> split(String expression){
                char[] temp = expression.toCharArray();
                String[] exp =new String[temp.length];
                for(int i=0;i<temp.length;i++){
                        exp[i]=String.valueOf(temp[i]);
                }
                ArrayList<String> expList = new ArrayList<String>();
                String value = "";
                for(int i=0;i<exp.length;i++){
                        if (symbol.contains(exp[i])){
                                if (value!="")
                                        expList.add(value);
                                expList.add(exp[i]);
                                value="";
                                continue;
                        }
                        value+=exp[i];
                        if(i==exp.length-1){ 
                                expList.add(value);
                        }
                }
                return expList;
        }
        //判断two的优先级是否不高于one,为true则栈弹出元素，否则进栈
        public boolean priority(String one,String two){
                if((one.equals("*")||one.equals("/"))&&(!two.equals("*")&&!two.equals("/"))) {
                        return true;
                }
                if((one.equals("+")||one.equals("-"))&&(!two.equals("*")&&!two.equals("/"))) {
                        return true;
                }
                else
                        return false;
        }
        //前缀表达式转后缀表达式
        public ArrayList<String> prefixToSuffix(ArrayList<String> prefix_list){
                Stack<String> stack = new Stack<String>();
                ArrayList<String> suffix_list = new ArrayList<String>();
                for(String item : prefix_list){
                if (!symbol.contains(item)){
                        //不是符号就输出
                        suffix_list.add(item);
                        continue;
                }
                if (item.equals("(")||stack.isEmpty()){
                        stack.push(item);
                        continue;
                }
                if(item.equals(")")){
                        while(!stack.peek().equals("(")){
                                suffix_list.add(stack.pop());
                        }
                        stack.pop();
                        continue;
                }
                while (priority(stack.peek(), item)){
                        suffix_list.add(stack.pop());
                        if (stack.isEmpty())
                                break;
                }
                stack.push(item);
                }
                while(!stack.isEmpty()){
                        suffix_list.add(stack.pop());
                }
                return suffix_list;
        }
        
        //计算后缀表达式
        public double cal(ArrayList<String> list){
                Stack<String> stack = new Stack<String>();
                for(String item : list){
                        if (!symbol.contains(item)){
                                stack.push(item);
                                continue;
                        }
                        else{
                                double one = Double.valueOf(stack.pop());
                                double two = Double.valueOf(stack.pop());
                                double value = 0;
                                if (item.equals("+")){
                                        value = two+one;
                                }
                                else if (item.equals("-")){
                                        value = two-one;
                                }
                                else if (item.equals("*")){
                                        value = two*one;
                                }
                                else if (item.equals("/")){
                                        value = two/one;
                                }
                                stack.push(String.valueOf(value));
                        }
                }
                return Double.valueOf(stack.pop());
        }
        public static void main(String[] args) throws IOException {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String expression = br.readLine();
                RPN rpn = new RPN();
                ArrayList<String> expList = rpn.split(expression);
                System.out.println(expList);
                ArrayList<String> suffix_list = rpn.prefixToSuffix(expList);
                System.out.println(suffix_list);
                System.out.println(rpn.cal(suffix_list));
        }
}