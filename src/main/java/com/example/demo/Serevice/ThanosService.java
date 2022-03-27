package com.example.demo.Serevice;

import com.example.demo.Entity.Thanos;
import com.example.demo.ExceptionHandling.ResourceNotFoundException;
import com.example.demo.Payload.request.ThanosRequest;
import com.example.demo.Repository.ThanosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class ThanosService implements thanosServiceInterface {

    @Autowired
    private final ThanosRepository thanosRepository;

    @Override
    public Thanos saveMember(ThanosRequest mem) {

        Thanos newMember = new Thanos(mem.getFirstName(), mem.getLastName(), mem.getDepartment(), mem.getPassword());

        return thanosRepository.save(newMember);
    }

    @Override
    public List<Thanos> getAllMembers(String sort) {
        return thanosRepository.findAll(Sort.by(sort));
    }

    @Override
    public Thanos getMemberById(int id) {
        return thanosRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMember(int id) {
        Thanos deleteMem = thanosRepository.findById(id).orElse(null);
        if (deleteMem == null){
            throw new ResourceNotFoundException("Not found id: " +id);
        }
        thanosRepository.delete(deleteMem);
    }

    @Override
    public Thanos updateMember(ThanosRequest mem, int id) {

        Thanos updateMember = new Thanos(mem.getFirstName(), mem.getLastName(), mem.getDepartment(), mem.getPassword());

//        check member if it existed
        Thanos existingMember = thanosRepository.findById(id).orElse(null);
        if (existingMember == null){
            return null;
        }
        existingMember.setFirstName(updateMember.getFirstName())
                .setLastName(updateMember.getLastName())
                .setDepartment(updateMember.getDepartment())
                .setPassword(updateMember.getPassword())
                .setEmail(updateMember.getEmail());

        return thanosRepository.save(existingMember);

    }

    @Override
    public List<Thanos> getPagingMembers(int startPage, int pageSize, String sort) {

        Pageable pageRequest = PageRequest.of(startPage, pageSize, Sort.by(sort));

        Page<Thanos> pageResult = thanosRepository.findAll(pageRequest);
        if (pageResult.hasContent()){
            return pageResult.getContent();
        } else {
            return null;
        }
    }

}
