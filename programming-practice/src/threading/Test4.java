package threading;


public class Test4 {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int[] n = new int[10001];
		for(int i=1;i<n.length;i++) {
			n[i]=1;
			
			for(int j=2;j<=Math.sqrt(i);j++) {
				if(i%j==0) n[i]++;
			}
		}
		int max=0;
		for(int i=0;i<n.length;i++) {
			if(max<n[i])
				max=n[i];
		}
		long end = System.currentTimeMillis();
		System.out.println(max);
		System.out.println("time : "+(end-start));
	}
}
