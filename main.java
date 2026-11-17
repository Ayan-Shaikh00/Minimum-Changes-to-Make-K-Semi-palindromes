
import java.util.*;

class Solution {

    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = 1000000;

        int[][] cost = new int[n][n];

        // Precompute semi-palindrome conversion costs
        for (int len = 2; len <= n; len++) {
            for (int l = 0; l + len <= n; l++) {

                int r = l + len - 1;
                cost[l][r] = INF;

                // Try every valid divisor
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) continue;

                    int changes = 0;

                    // Compare characters in each group
                    for (int i = 0; i < d; i++) {

                        int left = l + i;
                        int right = r - ((len - 1 - i) % d);

                        while (left < right) {
                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    cost[l][r] = Math.min(cost[l][r], changes);
                }
            }
        }

        // DP: dp[i][j] = minimum changes to
        // partition first i characters into j parts
        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k; j++) {

                for (int p = 0; p <= i - 2; p++) {

                    if (dp[p][j - 1] == INF) continue;

                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[p][j - 1] + cost[p][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
