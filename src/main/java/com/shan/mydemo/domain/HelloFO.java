package com.shan.mydemo.domain;

import lombok.*;

import java.util.Date;

/**
 * Hello form.
 *
 * @date 2020-12-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HelloFO {

    private String id ;

    private String hello;

    private Date date;
}
