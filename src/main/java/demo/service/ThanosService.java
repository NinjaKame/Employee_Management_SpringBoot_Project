package demo.service;

import demo.entity.Thanos;
import demo.exception.ResourceNotFoundException;
import demo.model.request.ThanosRequest;
import demo.repository.ThanosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service @RequiredArgsConstructor
public class ThanosService implements thanosServiceInterface, CommandLineRunner {

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

    @Override
    public void run(String... args) throws Exception {
        List<Thanos> thanosList = Arrays.asList(
                new Thanos("John","Cena","Mechanical", null),
                new Thanos("Phong","Tom","Civil",null),
                new Thanos("Huan","Rose","Biology",null),
                new Thanos("Lionel","Messi","Football",null),
                new Thanos("Kento","Momota","Badminton",null),
                new Thanos("Lee","Rock","Medical", null),
                new Thanos("Uzumaki","Naruto","DataScience",null),
                new Thanos("Thuan","Tran","Developer",null),
                new Thanos("Mario","Balotteli","Boxing",null),
                new Thanos("Harry","Maguire","RacingBoy",null)
        );
        thanosRepository.saveAll(thanosList);
    }
}
