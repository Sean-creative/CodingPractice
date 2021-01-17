
//day1 - 꼬리 물기 최적화


// 1. for문으로 -> 1~10 sum
// 2. 꼬리물기 최적화 적용 -> 1~10 sum
// 3. 꼬리물기 최적화가 적용X -> 1~10 sum
public class SubRoutineChain {
    public static void main(String[] args) {

        int num = 10;
        System.out.println(justFor(num));
        System.out.println(subRoutineChain(num, 0));
        System.out.println(notSubRoutineChain(num));
    }

    public static int justFor(int num) {
        int acc= 0;
        for (int i=1; i<=num; i++) acc += i;
        return acc;
    }

    // 2. 꼬리물기 최적화 적용 -> 1~10 sum
    public static int subRoutineChain(int num, int acc) {
        if (num==1) return num+acc;
        else return subRoutineChain(num-1, num+acc);
    }

    // 3. 꼬리물기 최적화가 적용X -> 1~10 sum
    public static int notSubRoutineChain(int num) {
        if (num==1) return 1;
        else return num + notSubRoutineChain(num-1);
    }
}
