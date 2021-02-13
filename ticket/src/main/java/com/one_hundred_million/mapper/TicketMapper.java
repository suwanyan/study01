package com.one_hundred_million.mapper;

import com.one_hundred_million.model.Ticket;

/**
 * @author suwanyan
 * @date 2020/9/22 22:02
 */
public interface TicketMapper {
    Ticket selectTicketByID (int id);
    int insertTicket (Ticket ticket);
}
