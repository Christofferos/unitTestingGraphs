# unitTestingGraphs

This is a project from the first year at KTH with focus on unit testing.

<br>
<br>

Task 3 - Time Cost Analysis

Complete the following table of time costs for different values of n, for both implementations.

| Size (n) | MatrixGraph   | HashGraph    |
| -------- | ------------- | ------------ |
| 100      | 1 210 425 ns  | 1 185 127 ns |
| 400      | 2 108 833 ns  | 1 596 231 ns |
| 1600     | 8 522 672 ns  | 2 870 808 ns |
| 6400     | 29 101 412 ns | 5 808 175 ns |

HashGraph shows significant preformance compared to MatrixGraph when the value of n becomes large!

<br>

| I got following times in nanoseconds for HashGraph (6400 vertices and 100 test repitions): |
| ------------------------------------------------------------------------------------------ |
| 1 716 982                                                                                  |

<br>

| I got following times in nanoseconds for MatrixGraph (6400 vertices and 100 test repitions): |
| -------------------------------------------------------------------------------------------- |
| 17 849 583                                                                                   |

<br>
**Explaination:**
<br>
HaphGraph had, surprisingly enough, faster execution times for evaluating the size of the largest component and the number of components for all sizes of n that was tested. However, the table shows great differences as the size of the input grows. This could be explained by the time complexity for iterating over the entire graph for respective graph, HashGraph has O(n+m) and MatrixGraph O(n^2). This causes MatrixGraph to preform worse with a remarkable amount in the later tests.
