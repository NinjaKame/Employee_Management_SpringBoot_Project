package com.example.demo.Serevice;

import com.example.demo.Entity.Thanos;
import com.example.demo.Payload.request.ThanosRequest;

import java.util.List;

public interface thanosServiceInterface{

    Thanos saveMember(ThanosRequest mem);

    List<Thanos> getAllMembers(String sort);

    Thanos getMemberById(int id);

    void deleteMember(int id);

    Thanos updateMember(ThanosRequest mem, int id);

    List<Thanos> getPagingMembers(int startPage, int pageSize, String sort);
}
