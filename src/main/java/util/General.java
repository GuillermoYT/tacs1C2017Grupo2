package util;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import apiResult.MovieCastResult;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;

public class General {

	public static List<MovieCastResult> findDuplicateIntegers(List<MovieCastResult> inList) {

		  List<MovieCastResult> listWithDuplicates = inList.stream().filter(i ->  inList.stream().filter(x -> ((Integer)x.getId()).equals((i.getId()))).count() >1)
				.collect(Collectors.toList());
		
		@SuppressWarnings("unchecked")
		List<MovieCastResult> unique = listWithDuplicates.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(MovieCastResult::getId))),
                                           ArrayList::new));
	
		return unique;
	}
	
}
