package PTA.p1081to1090;

public class Read_Number_in_Chinese {
    final static String[] numbers=new String[]{"ling","yi","er","san","si","wu",
            "liu","qi","ba","jiu"};
    final static String[] bitSegments=new String[]{"","Wan","Yi"};
    final static String[] segments=new String[]{"Shi","Bai","Qian"};
    static class NumberFormat{
        String sign="";

        public NumberFormat(String numberString) {
            StringBuilder builder=new StringBuilder(numberString);
            if(builder.charAt(0)=='-'){
                builder.deleteCharAt(0);
                sign="-";
            }
            for (int i = builder.length()-1; i >=0; i-=4) {
                String substring = builder.substring(i - 3 < 0 ? 0 : i - 3, i);

            }
        }
    }
    public static void main(String[] args) {

    }
}
