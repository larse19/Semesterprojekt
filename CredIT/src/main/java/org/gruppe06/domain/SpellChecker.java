package org.gruppe06.domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SpellChecker {
    private ProgramSystem programSystem = new ProgramSystem();
    private CastMemberSystem castMemberSystem = new CastMemberSystem();
    private ProducerSystem producerSystem = new ProducerSystem();

    private final String ABC = "abcdefghijklmnopqrstuvwxyzæøå1234567890";
    private Map<String, Integer> dictionary = new HashMap<>();
    private String DICTIONARY_VALUES;


    public SpellChecker(){
        DICTIONARY_VALUES = programSystem.getListOfProgramNames().toString().replaceAll("\\s+",",")
                .replaceAll("\\[","").replaceAll("]", "");
        DICTIONARY_VALUES += castMemberSystem.getListOfCastMembers().toString().replaceAll("\\s+",",")
               .replaceAll("\\[","").replaceAll("]", "");
        DICTIONARY_VALUES += producerSystem.getListOfProducers().toString().replaceAll("\\s+",",")
                .replaceAll("\\[","").replaceAll("]", "");

        System.out.println(DICTIONARY_VALUES);
    }

    public Map<String, Integer> getDictionary() {
        return dictionary;
    }

    public String getDICTIONARY_VALUES() {
        return DICTIONARY_VALUES;
    }

    public void setDictionaryValues(String dictionaryValues) {
        this.DICTIONARY_VALUES = dictionaryValues;
    }

    private Stream<String> getStringStream(String word) {
        Stream<String> deletes = IntStream.range(0, word.length()).mapToObj((i) ->
                word.substring(0, i) + word.substring(i + 1));
        Stream<String> replaces = IntStream.range(0, word.length()).boxed().flatMap((i) ->
                ABC.chars().mapToObj((c) -> word.substring(0, i) + (char) c + word.substring(i + 1)));
        Stream<String> inserts = IntStream.range(0, word.length() + 1).boxed().flatMap((i) ->
                ABC.chars().mapToObj((c) -> word.substring(0, i) + (char) c + word.substring(i)));
        Stream<String> transposes = IntStream.range(0, word.length() - 1).mapToObj((i) ->
                word.substring(0, i) + word.substring(i + 1, i + 2) + word.charAt(i) + word.substring(i + 2));
        return Stream.of(deletes, replaces, inserts, transposes).flatMap((x) -> x);
    }

    private Stream<String> edits1(final String word) {
        return getStringStream(word);
    }

    public String correct(String word) {
        Optional<String> e1 = known(edits1(word)).max(Comparator.comparingInt(a -> dictionary.get(a)));
        if (e1.isPresent()) return dictionary.containsKey(word) ? word : e1.get();
        Optional<String> e2 = known(edits1(word).map(this::edits1).flatMap((x) -> x)).max(Comparator.comparingInt(a -> dictionary.get(a)));
        return (e2.orElse(word));
    }

    private Stream<String> known(Stream<String> words) {
        return words.filter((word) -> dictionary.containsKey(word));
    }
}
