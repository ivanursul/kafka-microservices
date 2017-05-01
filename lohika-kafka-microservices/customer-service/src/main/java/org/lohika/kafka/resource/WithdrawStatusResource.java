package org.lohika.kafka.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawStatusResource {

    private String login;
    private WithdrawStatus status;

}
