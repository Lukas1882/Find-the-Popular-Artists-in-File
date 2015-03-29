# Find The Popular Artists

***Question:***

The attached text file "Artist_lists_small.txt" contains the favorite musical artists of 1000 users from LastFM. Each line is a list of up to 50 artists, formatted as follows:

Radiohead,Pulp,Morrissey,Delays,Stereophonics,Blur,Suede,Sleeper,The La's,Super Furry Animals\n
Band of Horses,Iggy Pop,The Velvet Underground,Radiohead,The Decemberists,Morrissey,Television\n
etc.

Write a program that, using this file as input, produces an output file containing a list of pairs of artists which appear TOGETHER in at least fifty different lists. For example, in the above sample, Radiohead and Morrissey appear together twice, but every other pair appears only once.

Your solution cannot store a list of all possible pairs of bands (don't use a 'brute force' approach). Your solution MAY return a best guess, i.e. lists which appear at least 50 times with high probability, as long as you explain why this tradeoff improves the performance of the algorithm. Please include, either in comments or in a separate file, a brief one-paragraph description of any optimizations you made and how they impact the run-time of the algorithm.

***My Solution***

For me, this is a kind of probability problem.

Before we start, let we suppose that the artists who appear in the list not less than 50 times as "Big Group", the other artists as "Small Group".

Firstly, let us focus on a single artist. We suppose that artist is "Lady Gaga". In worst case, only 50 lists of all the list have the name of "Lady Gaga". Therefore, the probability of selecting one list randomly owns LG("Lady Gaga") is ***50/1000***, that is ***1/20***. In mathematics, if we select a random list repeat N times, the expectation of that is N/20. In order make sure that we can identify LG, we need ***N/20 >= 1***, that is ***N>=20***. In this way, if we pick ***21*** lists and get some artists more than 1 time, there is a probability that those artists are from "Big Group".

Now, it is the time for math. In worst case, if we select a list the probability that we get LG is 1/20( of course, in this case, LG is in Big Group). If we select ***21*** times, the probability that we can get LG twice is ***P(twice) = (1 - P0 - P1)***, while P1 is the probability that we get LG 1 time and P0 is the probability we don't get LG. ***P0 = (19/20)^21^***, ***P1 = (1/20)^20^x(19/20)***, and P(twice) is around ***0.6415***. 

In the same way, if we have a artist "Bird" in SG, in best case, the probability that we get "Bird" tiwce from 21 times pick is ***(1/20)^2^x(19/20)^19^x(19x18/2)***, around ***0.1613***.

Let's do conditional probability!

Still in worst case, the probability that we select one list and that list have LG is ***P<sub>B</sub> = 1/20***. Here the B means "Big Group". The probability we get a artist from SG is ***P<sub>S</sub> = 1/20***, too. By the above steps, we can find if the artist is from BG, and the name get tiwce from our 21 picks, the conditional probability is ***P<sub>B</sub>^T^ ＝ 0.6415***. The conditional probability that if one artist is from SG, we get him tiwce is ***P<sub>S</sub>^T^ ＝ 0.1613.*** Here, our goal is to get the probabiliy that if we get a artist twice this artist is in BG, that is ***P<sub>T</sub>^B^***. 

We have ***P<sub>B</sub>^T^xP<sub>B</sub> = P^TB^*** , 
***P<sub>T</sub>^B^ = P^TB^/P<sub>T</sub>***, and ***P<sub>T</sub> = P(twice)*** (Since ***P<sub>B</sub> = P<sub>S</sub>***). Finally we get ***P<sub>T</sub>^B^ =  0.1988530***.

If we run our random 21 list pick N times, we know that the artist in BG will appear more than ***N x P<sub>T</sub>^B^ *** times. In this way, if any artist appear more than ***N x P<sub>T</sub>^B^ *** times, we can put this artist to our final guess result.

## About the Java Code and Result
In my Java result, I set the default round number is 1000. Base on this, my guess algorith get a name list of artists size around 170 while the real artists size who appear not less than 50 is 121. I compare the value in my guess and true list, the accuracy is around 70%. The accuracy will be a little higher if we set the round number bigger.

In my result, my guess have more artists than the true list. To gain more accuracy, we can set the strictGap to a higher value. ***P<sub>T</sub>^B^ = P<sub>T</sub>^B^ + strictGap***. If we set ***strictGap = 0.08***, the accuracy can be more than ***90%***. User can make a balance of the accuracy and result number.