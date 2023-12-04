package graph;

public class FloydWarshallTest {

}
class SolutionTest5 {
    public int[][] shortestDistance(int[][] m) {
        int lim=(int)1e9;
        int n=m.length;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(i==j)
                    m[i][j]=0;

                else if(m[i][j]==-1)
                    m[i][j]=lim;
            }
        }

        for(int k=0;k<n;k++) {
            for(int i=0;i<n;i++) {
                for(int j=0;j<n;j++) {
                    m[i][j]=Math.min(m[i][j],
                            m[i][k] + m[k][j]);
                }
            }
        }

        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(m[i][j]==lim)
                    m[i][j]=-1;
            }
        }
        return m;
    }
}