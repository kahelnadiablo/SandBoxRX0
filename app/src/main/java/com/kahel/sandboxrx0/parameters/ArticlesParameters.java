package com.kahel.sandboxrx0.parameters;

import lombok.Data;

/**
 * Created by Mark on 8/18/2015.
 */
@Data
public class ArticlesParameters {
    private final String Articles =  "/mobileapp/v1/entertainment/articles/all/";
    private int pageNumber;
}
