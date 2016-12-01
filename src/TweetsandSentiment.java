import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.ArrayList;
import java.util.Scanner;
public class TweetsandSentiment{

	private static Scanner console;



	public static void  main(String... args) throws TwitterException{

		Twitter twitter = TwitterFactory.getSingleton();
		ArrayList<String> ar = new ArrayList<String>();
		ArrayList<String> ar2 = new ArrayList<String>();
		Sentimenter.init();
		System.out.println("Hello, Welcome to Twitter Sentiments");
		System.out.println("Please Enter Search Query");

		console = new Scanner(System.in);

		double average = 0;
		double TweetCounter = 0;


		String v = console.nextLine();

		if(v != null){
			System.out.println("Thank You. Searching....");
			System.out.println();

			Query query = new Query(v);
			query.setCount(100);
			QueryResult result = twitter.search(query);
			while(result.hasNext()) {


				for (Status status : result.getTweets()) {
					System.out.println(status.getText());

					String s = status.getText();
					ar.add("Unoptimized: " +s);
					String k = Optimize(s);
					System.out.println(k);
					ar.add("Optimized: " + k);

					TweetCounter++;

					
					if (Sentimenter.SentimentCalculator(k) == 3){
						System.out.println("This Tweet is Positive!");
						average = average + 1;

					}


					else	if (Sentimenter.SentimentCalculator(k) == 2){
						System.out.println("This Tweet is Neutraul.");


					}


					else if (Sentimenter.SentimentCalculator(k) == 1){
						System.out.println("This Tweet is Negative!");
						average = average - 1;

					}


					if(TweetCounter == 50){
						break;
					}


				}

				if(TweetCounter == 50){
					break;
				}
				query = result.nextQuery();
				result = twitter.search(query);


			}






		}

		for (String p : ar)
		    System.out.println(p);
		
		for (String z : ar2)
		    System.out.println(z);
		
		System.out.println("Number of Tweets: " + TweetCounter); 
		System.out.println("Average Sentiment: " + average/TweetCounter); 

		String y;
		if(average/TweetCounter > 0){
			y = "Postive";
		}

		else if (average/TweetCounter < 0){

			y = "Negative";
		}

		else{
			y = "Nuetral";
		}


		System.out.println("The Average Sentiment for " + v + " is " + y); 


	}



	public static String Optimize(String b){

		b = b.replace(".", "");
		b = b.replace("#", "");

		b = b.replaceFirst("RT", "");


		int count = 0;	
		String start;
		for(int i = 0; i<b.length(); i++){

			if(b.charAt(i)== '@' ){



				if(i== 0){

					count = 0;

					while (b.charAt(count) != ' ' &&  count!= (b.length()-1 )){					

						count++;


					}

					b = b.substring(count, b.length());

					i = 0;

				}








				else{


					count = i;







					while(b.charAt(count) != ':' &&  b.charAt(count) != ' '  && count!= (b.length()-1 )){					

						count++;


					}

					start = b.substring(0,i);	

					b = b.substring(count+1, b.length());

					b = start + b; 

					i = 0;



				}
			}
			else if(b.charAt(i)== 'h'){

				if(i+1 != b.length() |  i+1 < b.length())   {


					if(b.charAt(i+1)== 't'){

						if(i+2 != b.length() |  i+2 < b.length())   {

							if(b.charAt(i+2)== 't'){

								b = b.substring(0,i);

							}
						}
					}
				}

			}



		}


		return b;

	}



}
