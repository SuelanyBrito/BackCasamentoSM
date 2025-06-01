package com.casamento.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ObjectList {
    @JsonProperty("results")
    private List<Element> results = Collections.emptyList();

    @Getter
    @Setter
    public static class Element {
        @JsonProperty("id")
        private String id;

        @JsonProperty("properties")
        private Properties properties;

        @Getter
        @Setter
        public static class Properties {
            @JsonProperty("Marcado")
            private Marcado marcado;

            @JsonProperty("Progress")
            private Progress progress;

            @JsonProperty("TitleProperty")
            private TitleProperty titleProperty;

            @JsonProperty("Qtde")
            private Qtde qtde;

            @Getter
            @Setter
            private static class Qtde {
                @JsonProperty("number")
                private int number;
            }

            @Getter
            @Setter
            public static class Marcado{
                @JsonProperty("checkbox")
                private Boolean checkbox;
            }

            @Getter
            @Setter
            public static class Progress {
                @JsonProperty("select")
                private Select select;

                @Getter
                @Setter
                public static class Select {
                    @JsonProperty("name")
                    private String name;
                }
            }

            @Getter
            @Setter
            public static class TitleProperty {
                @JsonProperty("title")
                private List<Title> title;

                @Getter
                @Setter
                public static class Title {
                    @JsonProperty("plain_text")
                    private String plain_text;
                }
            }
        }
    }
}
