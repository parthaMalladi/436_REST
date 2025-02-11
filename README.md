## Application Details

The way I implemented program 2 involves making API calls based on input from the user. My main() method takes in a console argument
in the form of a city name. I have 3 URLs or API links that I insert the city name into, along with any necessary request headers. For
example, two of my API's require thei API key to sent in as a request header instead of being part of the URL. I do so via a 2D array
which contains the key-value pairings, which is an input parameter for a function called getInfo(). getInfo takes in the 2D header array
along with the URL for the api and a keyword used to find the necessary information from the JSON response. The way I parse the JSON is
very simple. I have a helper function called getValue() which finds the key in JSON string, then reads all the values after the key upto
the next key. Then it returns that substring. This substring is also the return value of the getInfo() method. If the user inputs a
bad input such as a mispelled city name, then there are safeties that catch any errors through an if statement and I tell the user to 
re-enter the city name.

## Application flow
1) User compiles and runs the program, then enters the city name that they want info about.
2) City name is stored and put into each of the 3 API calls/URLs along with each API's request header information.
3) getInfo() method is called, which calls getHTML() to get the JSON response.
4) getInfo() also takes in a key string in order to find the required info from the JSON response. This is done through
another helper function called getValue().
5) The return value of getValue() is sent back as the return value of getInfo(). If the return is correct, the info regarding
the city is displayed to the user. Otherwise they are told to re-enter the city name due to an error.