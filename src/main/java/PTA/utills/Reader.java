//package PTA.utills;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class Reader {
//    class Pair {
//        boolean status;
//        int value;
//
//        private Pair(boolean status, int value) {
//            this.status = status;
//            this.value = value;
//        }
//
//        Pair set(boolean status, int value) {
//            this.status = status;
//            this.value = value;
//            return this;
//        }
//    }
//
//    private Pair pair = new Pair(false, 0);
//    private InputStream streamIn = System.in;
//
//    private Pair readInt() throws IOException {
//        int base = 0;
//        boolean negative = false;
//        boolean lineEnd = false;
//        while (true) {
//            int c = streamIn.read();
//            switch (c) {
//                case ' ':
//                    break;
//                case '\n':
//                    lineEnd = true;
//                    break;
//                case '-':
//                    negative = true;
//                    continue;
//                default:
//                    base = base * 10 + (c - 48);
//                    continue;
//            }
//            if (negative) base = -base;
//            return pair.set(lineEnd, base);
//        }
//    }
//
//    public int[] readLine2IntArray(int initSize) throws IOException {
//        int[] result = new int[initSize];
//        for (int i = 0; i < initSize; i++) {
//            result[i] = readInt().value;
//        }
//        return result;
//    }
//}