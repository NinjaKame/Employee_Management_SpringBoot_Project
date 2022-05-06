package demo.controller;

import demo.entity.Thanos;
import demo.exception.ResourceNotFoundException;
import demo.model.request.ThanosRequest;
import demo.model.response.ThanosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/thanos")
public class ThanosController {

    @Autowired
    private demo.service.thanosServiceInterface thanosServiceInterface;

    @GetMapping("/all")
    public ResponseEntity<List<ThanosResponse>> getAllThanos(
            @RequestParam(defaultValue = "id") String sort) {

        List<ThanosResponse> result = thanosServiceInterface.getAllMembers(sort)
                .stream().map(ThanosResponse::new).collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThanosResponse> getThanosByID(@PathVariable int id) {
        Thanos newThanos = thanosServiceInterface.getMemberById(id);

        if (newThanos == null) {
            System.out.println("Ko tìm thấy id: "+ id);
            throw new ResourceNotFoundException("Not found id: "+ id);
        }

        ThanosResponse thanosResponse = new ThanosResponse(newThanos);

        return new ResponseEntity<>(thanosResponse, HttpStatus.FOUND);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ThanosResponse>> getThanosInPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sort) {

        List<ThanosResponse> result = thanosServiceInterface.getPagingMembers(page, size, sort)
                .stream().map(ThanosResponse::new).collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<ThanosResponse> addThanos(@RequestBody ThanosRequest thanosRequest) {
        // payload request
        // ModelToEntity mapper
        Thanos newThanos = thanosServiceInterface.saveMember(thanosRequest);

        ThanosResponse thanosResponse = new ThanosResponse(newThanos);

        return new ResponseEntity<>(thanosResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThanos(@PathVariable int id) {
        thanosServiceInterface.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThanosResponse> updateThanos(@PathVariable int id, @RequestBody ThanosRequest mem) {

        Thanos newThanos = thanosServiceInterface.updateMember(mem, id);

        if (newThanos == null) {
            System.out.println("Ko tìm thấy id: "+ id);
            throw new ResourceNotFoundException("Not found id: " + id);
        }

        ThanosResponse thanosResponse = new ThanosResponse(newThanos);

        return new ResponseEntity<>(thanosResponse, HttpStatus.OK);
    }
}
