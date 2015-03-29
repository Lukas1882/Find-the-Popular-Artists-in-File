package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static LinkedList<String> artistList;
    private static int repeattime = 50;
    private static int randNum = 21;
    private static int lineNum = 1000;
    private static int around = 500;
    // strictGap is from 0.00 to (1-expectPercent), the value is bigger, the result of guess will be smaller, then get more accuracy.
    // When it gets more accuracy, we lose the number of result. User can make a balance of accuracy and result number.
    private static double strictGap = 0.08;
    private static double exspectPercent = 0.1988530;

    public static void main(String[] args) {
        artistList = loadArtistList();
        LinkedList<String> megaResult = megaStatistics(artistList);
        LinkedList<String> actualList = actualArtistList(artistList);
        Collections.sort(megaResult);
        Collections.sort(actualList);
        System.out.println("The size of actual artist list is " + actualList.size());
        System.out.println("The size of calculation artist list is " + megaResult.size());
        System.out.println("The accuracy is " + getAccuracy(actualList, megaResult));
        System.out.println("Don't like this result? Try to set the parameter \"strictGap\" from \"0\" to a bigger value to have more accuracy.");

    }

    public static double getAccuracy(LinkedList<String> actualList, LinkedList<String> calculatedList) {
        double calculatedNum = calculatedList.size();
        for (String artist : actualList) {
            if (calculatedList.contains(artist)) {
                calculatedList.remove(artist);
            }
        }
        double errorNum = calculatedList.size();
        return (calculatedNum - errorNum) / calculatedNum;

    }

    // run N round to get a guess of the artists
    public static LinkedList<String> megaStatistics(LinkedList<String> artistList) {
        LinkedList<String> artists = new LinkedList<>();
        LinkedList<String> sampleArtistList;
        HashMap<String, Integer> artistMap = new HashMap<>();
        for (int i = 0; i < around; i++) {
            sampleArtistList = randomRepeatedArtist(artistList);
            for (String artist : sampleArtistList) {
                if (artistMap.containsKey(artist)) {
                    artistMap.put(artist, artistMap.get(artist) + 1);
                } else {
                    artistMap.put(artist, 1);
                }
            }
        }
        Iterator it = artistMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (Integer.parseInt(pair.getValue().toString()) >= (around * (exspectPercent + strictGap))) {
                artists.add(pair.getKey().toString());
            }
        }
        return artists;
    }

    // Read lists from file to ArrayList
    public static LinkedList<String> loadArtistList() {
        LinkedList<String> artistList = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/company/Artist_lists_small.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                artistList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artistList;
    }

    // Get the real list of artists appear more than 50 times.
    public static LinkedList<String> actualArtistList(LinkedList<String> lists) {
        LinkedList<String> actualList = new LinkedList<>();
        HashMap<String, Integer> nameMap = new HashMap<>();
        for (String list : lists) {
            String[] nameList = list.split(",");
            for (String name : nameList) {
                if (nameMap.containsKey(name)) {
                    if (nameMap.get(name) == 0) {
                        continue;
                    }
                    if (nameMap.get(name) == repeattime) {
                        actualList.add(name);
                        nameMap.put(name, 0);
                    } else {
                        nameMap.put(name, nameMap.get(name) + 1);
                    }
                } else {
                    nameMap.put(name, 1);
                }
            }
        }
        return actualList;
    }

    //get random lists from the file, to check the repeated artist
    public static LinkedList<String> randomRepeatedArtist(LinkedList<String> artistList) {
        LinkedList<String> randLists = new LinkedList<>();
        Random rn = new Random();
        int randIndex;
        LinkedList<Integer> indexList = new LinkedList<>();
        while (indexList.size() < randNum) {
            randIndex = rn.nextInt(lineNum);
            if (!indexList.contains(randIndex)) {
                indexList.add(randIndex);
                randLists.add(artistList.get(randIndex));
            }
        }
        return repeatedArtists(randLists);
    }

    // get the artists appear more than once
    public static LinkedList<String> repeatedArtists(LinkedList<String> lists) {
        LinkedList<String> repeatedList = new LinkedList<>();
        HashMap<String, Integer> nameMap = new HashMap<>();
        for (String list : lists) {
            String[] nameList = list.split(",");
            for (String name : nameList) {
                if (nameMap.containsKey(name)) {
                    if (nameMap.get(name) == 0) {
                        continue;
                    }
                    repeatedList.add(name);
                    nameMap.put(name, 0);
                } else {
                    nameMap.put(name, 1);
                }
            }
        }
        return repeatedList;
    }
}
