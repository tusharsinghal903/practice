package org.example.models;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuickestConnection {
    Long timeRequired;
    List<Integer> flights;

}
