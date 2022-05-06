package demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import demo.entity.Thanos;
import demo.exception.ResourceNotFoundException;
import demo.model.request.ThanosRequest;
import demo.repository.ThanosRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ThanosService.class})
@ExtendWith(SpringExtension.class)
class ThanosServiceTest {
    @MockBean
    private ThanosRepository thanosRepository;

    @Autowired
    private ThanosService thanosService;

    /**
     * Method under test: {@link ThanosService#saveMember(ThanosRequest)}
     */
    @Test
    void testSaveMember() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        when(this.thanosRepository.save((Thanos) any())).thenReturn(thanos);
        assertSame(thanos, this.thanosService.saveMember(new ThanosRequest("Jane", "Doe", "Department", "iloveyou")));
        verify(this.thanosRepository).save((Thanos) any());
    }

    /**
     * Method under test: {@link ThanosService#saveMember(ThanosRequest)}
     */
    @Test
    void testSaveMember2() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        when(this.thanosRepository.save((Thanos) any())).thenReturn(thanos);
        assertSame(thanos, this.thanosService.saveMember(new ThanosRequest()));
        verify(this.thanosRepository).save((Thanos) any());
    }

    /**
     * Method under test: {@link ThanosService#saveMember(ThanosRequest)}
    @Test
    @Disabled("TODO: Complete this test")
    void testSaveMember3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at demo.service.ThanosService.saveMember(ThanosService.java:26)
        //   In order to prevent saveMember(ThanosRequest)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   saveMember(ThanosRequest).
        //   See https://diff.blue/R013 to resolve this issue.

        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        when(this.thanosRepository.save((Thanos) any())).thenReturn(thanos);
        this.thanosService.saveMember(null);
    }*/

    /**
     * Method under test: {@link ThanosService#saveMember(ThanosRequest)}
     */
    @Test
    void testSaveMember4() {
        when(this.thanosRepository.save((Thanos) any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class,
                () -> this.thanosService.saveMember(new ThanosRequest("Jane", "Doe", "Department", "iloveyou")));
        verify(this.thanosRepository).save((Thanos) any());
    }

    /**
     * Method under test: {@link ThanosService#getAllMembers(String)}
     */
    @Test
    void testGetAllMembers() {
        ArrayList<Thanos> thanosList = new ArrayList<>();
        when(this.thanosRepository.findAll((org.springframework.data.domain.Sort) any())).thenReturn(thanosList);
        List<Thanos> actualAllMembers = this.thanosService.getAllMembers("Sort");
        assertSame(thanosList, actualAllMembers);
        assertTrue(actualAllMembers.isEmpty());
        verify(this.thanosRepository).findAll((org.springframework.data.domain.Sort) any());
    }

    /**
     * Method under test: {@link ThanosService#getAllMembers(String)}
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllMembers2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Property must not null or empty!
        //       at org.springframework.data.domain.Sort$Order.<init>(Sort.java:457)
        //       at org.springframework.data.domain.Sort$Order.<init>(Sort.java:396)
        //       at org.springframework.data.domain.Sort.lambda$new$0(Sort.java:71)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:195)
        //       at java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
        //       at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:913)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:578)
        //       at org.springframework.data.domain.Sort.<init>(Sort.java:72)
        //       at org.springframework.data.domain.Sort.by(Sort.java:87)
        //       at demo.service.ThanosService.getAllMembers(ThanosService.java:33)
        //   In order to prevent getAllMembers(String)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   getAllMembers(String).
        //   See https://diff.blue/R013 to resolve this issue.

        when(this.thanosRepository.findAll((org.springframework.data.domain.Sort) any())).thenReturn(new ArrayList<>());
        this.thanosService.getAllMembers("");
    }*/

    /**
     * Method under test: {@link ThanosService#getAllMembers(String)}
     */
    @Test
    void testGetAllMembers3() {
        when(this.thanosRepository.findAll((org.springframework.data.domain.Sort) any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> this.thanosService.getAllMembers("Sort"));
        verify(this.thanosRepository).findAll((org.springframework.data.domain.Sort) any());
    }

    /**
     * Method under test: {@link ThanosService#getMemberById(int)}
     */
    @Test
    void testGetMemberById() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        Optional<Thanos> ofResult = Optional.of(thanos);
        when(this.thanosRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(thanos, this.thanosService.getMemberById(1));
        verify(this.thanosRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link ThanosService#getMemberById(int)}
     */
    @Test
    void testGetMemberById2() {
        when(this.thanosRepository.findById((Integer) any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> this.thanosService.getMemberById(1));
        verify(this.thanosRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link ThanosService#deleteMember(int)}
     */
    @Test
    void testDeleteMember() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        Optional<Thanos> ofResult = Optional.of(thanos);
        doNothing().when(this.thanosRepository).delete((Thanos) any());
        when(this.thanosRepository.findById((Integer) any())).thenReturn(ofResult);
        this.thanosService.deleteMember(1);
        verify(this.thanosRepository).findById((Integer) any());
        verify(this.thanosRepository).delete((Thanos) any());
    }

    /**
     * Method under test: {@link ThanosService#deleteMember(int)}
     */
    @Test
    void testDeleteMember2() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        Optional<Thanos> ofResult = Optional.of(thanos);
        doThrow(new ResourceNotFoundException("An error occurred")).when(this.thanosRepository).delete((Thanos) any());
        when(this.thanosRepository.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(ResourceNotFoundException.class, () -> this.thanosService.deleteMember(1));
        verify(this.thanosRepository).findById((Integer) any());
        verify(this.thanosRepository).delete((Thanos) any());
    }

    /**
     * Method under test: {@link ThanosService#deleteMember(int)}
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteMember3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at demo.service.ThanosService.deleteMember(ThanosService.java:43)
        //   In order to prevent deleteMember(int)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   deleteMember(int).
        //   See https://diff.blue/R013 to resolve this issue.

        doNothing().when(this.thanosRepository).delete((Thanos) any());
        when(this.thanosRepository.findById((Integer) any())).thenReturn(null);
        this.thanosService.deleteMember(1);
    }*/

    /**
     * Method under test: {@link ThanosService#deleteMember(int)}
     */
    @Test
    void testDeleteMember4() {
        doNothing().when(this.thanosRepository).delete((Thanos) any());
        when(this.thanosRepository.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> this.thanosService.deleteMember(1));
        verify(this.thanosRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link ThanosService#updateMember(ThanosRequest, int)}
     */
    @Test
    void testUpdateMember() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        Optional<Thanos> ofResult = Optional.of(thanos);

        Thanos thanos1 = new Thanos();
        thanos1.setDepartment("Department");
        thanos1.setEmail("jane.doe@example.org");
        thanos1.setFirstName("Jane");
        thanos1.setId(1);
        thanos1.setLastName("Doe");
        thanos1.setPassword("iloveyou");
        when(this.thanosRepository.save((Thanos) any())).thenReturn(thanos1);
        when(this.thanosRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(thanos1, this.thanosService.updateMember(new ThanosRequest("Jane", "Doe", "Department", "iloveyou"), 1));
        verify(this.thanosRepository).save((Thanos) any());
        verify(this.thanosRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link ThanosService#updateMember(ThanosRequest, int)}
     */
    @Test
    void testUpdateMember2() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        Optional<Thanos> ofResult = Optional.of(thanos);
        when(this.thanosRepository.save((Thanos) any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(this.thanosRepository.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(ResourceNotFoundException.class,
                () -> this.thanosService.updateMember(new ThanosRequest("Jane", "Doe", "Department", "iloveyou"), 1));
        verify(this.thanosRepository).save((Thanos) any());
        verify(this.thanosRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link ThanosService#updateMember(ThanosRequest, int)}
     */
    @Test
    void testUpdateMember3() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");

        Thanos thanos1 = new Thanos();
        thanos1.setDepartment("Department");
        thanos1.setEmail("jane.doe@example.org");
        thanos1.setFirstName("Jane");
        thanos1.setId(1);
        thanos1.setLastName("Doe");
        thanos1.setPassword("iloveyou");

        Thanos thanos2 = new Thanos();
        thanos2.setDepartment("Department");
        thanos2.setEmail("jane.doe@example.org");
        thanos2.setFirstName("Jane");
        thanos2.setId(1);
        thanos2.setLastName("Doe");
        thanos2.setPassword("iloveyou");

        Thanos thanos3 = new Thanos();
        thanos3.setDepartment("Department");
        thanos3.setEmail("jane.doe@example.org");
        thanos3.setFirstName("Jane");
        thanos3.setId(1);
        thanos3.setLastName("Doe");
        thanos3.setPassword("iloveyou");

        Thanos thanos4 = new Thanos();
        thanos4.setDepartment("Department");
        thanos4.setEmail("jane.doe@example.org");
        thanos4.setFirstName("Jane");
        thanos4.setId(1);
        thanos4.setLastName("Doe");
        thanos4.setPassword("iloveyou");

        Thanos thanos5 = new Thanos();
        thanos5.setDepartment("Department");
        thanos5.setEmail("jane.doe@example.org");
        thanos5.setFirstName("Jane");
        thanos5.setId(1);
        thanos5.setLastName("Doe");
        thanos5.setPassword("iloveyou");
        Thanos thanos6 = mock(Thanos.class);
        when(thanos6.setDepartment((String) any())).thenReturn(thanos);
        when(thanos6.setEmail((String) any())).thenReturn(thanos1);
        when(thanos6.setFirstName((String) any())).thenReturn(thanos2);
        when(thanos6.setId(anyInt())).thenReturn(thanos3);
        when(thanos6.setLastName((String) any())).thenReturn(thanos4);
        when(thanos6.setPassword((String) any())).thenReturn(thanos5);
        thanos6.setDepartment("Department");
        thanos6.setEmail("jane.doe@example.org");
        thanos6.setFirstName("Jane");
        thanos6.setId(1);
        thanos6.setLastName("Doe");
        thanos6.setPassword("iloveyou");
        Optional<Thanos> ofResult = Optional.of(thanos6);

        Thanos thanos7 = new Thanos();
        thanos7.setDepartment("Department");
        thanos7.setEmail("jane.doe@example.org");
        thanos7.setFirstName("Jane");
        thanos7.setId(1);
        thanos7.setLastName("Doe");
        thanos7.setPassword("iloveyou");
        when(this.thanosRepository.save((Thanos) any())).thenReturn(thanos7);
        when(this.thanosRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(thanos7, this.thanosService.updateMember(new ThanosRequest("Jane", "Doe", "Department", "iloveyou"), 1));
        verify(this.thanosRepository).save((Thanos) any());
        verify(this.thanosRepository).findById((Integer) any());
        verify(thanos6).setDepartment((String) any());
        verify(thanos6).setEmail((String) any());
        verify(thanos6, atLeast(1)).setFirstName((String) any());
        verify(thanos6).setId(anyInt());
        verify(thanos6).setLastName((String) any());
        verify(thanos6).setPassword((String) any());
    }

    /**
     * Method under test: {@link ThanosService#updateMember(ThanosRequest, int)}
     */
    @Test
    void testUpdateMember4() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");

        Thanos thanos1 = new Thanos();
        thanos1.setDepartment("Department");
        thanos1.setEmail("jane.doe@example.org");
        thanos1.setFirstName("Jane");
        thanos1.setId(1);
        thanos1.setLastName("Doe");
        thanos1.setPassword("iloveyou");

        Thanos thanos2 = new Thanos();
        thanos2.setDepartment("Department");
        thanos2.setEmail("jane.doe@example.org");
        thanos2.setFirstName("Jane");
        thanos2.setId(1);
        thanos2.setLastName("Doe");
        thanos2.setPassword("iloveyou");

        Thanos thanos3 = new Thanos();
        thanos3.setDepartment("Department");
        thanos3.setEmail("jane.doe@example.org");
        thanos3.setFirstName("Jane");
        thanos3.setId(1);
        thanos3.setLastName("Doe");
        thanos3.setPassword("iloveyou");

        Thanos thanos4 = new Thanos();
        thanos4.setDepartment("Department");
        thanos4.setEmail("jane.doe@example.org");
        thanos4.setFirstName("Jane");
        thanos4.setId(1);
        thanos4.setLastName("Doe");
        thanos4.setPassword("iloveyou");

        Thanos thanos5 = new Thanos();
        thanos5.setDepartment("Department");
        thanos5.setEmail("jane.doe@example.org");
        thanos5.setFirstName("Jane");
        thanos5.setId(1);
        thanos5.setLastName("Doe");
        thanos5.setPassword("iloveyou");

        Thanos thanos6 = new Thanos();
        thanos6.setDepartment("Department");
        thanos6.setEmail("jane.doe@example.org");
        thanos6.setFirstName("Jane");
        thanos6.setId(1);
        thanos6.setLastName("Doe");
        thanos6.setPassword("iloveyou");

        Thanos thanos7 = new Thanos();
        thanos7.setDepartment("Department");
        thanos7.setEmail("jane.doe@example.org");
        thanos7.setFirstName("Jane");
        thanos7.setId(1);
        thanos7.setLastName("Doe");
        thanos7.setPassword("iloveyou");
        Thanos thanos8 = mock(Thanos.class);
        when(thanos8.setDepartment((String) any())).thenReturn(thanos2);
        when(thanos8.setEmail((String) any())).thenReturn(thanos3);
        when(thanos8.setFirstName((String) any())).thenReturn(thanos4);
        when(thanos8.setId(anyInt())).thenReturn(thanos5);
        when(thanos8.setLastName((String) any())).thenReturn(thanos6);
        when(thanos8.setPassword((String) any())).thenReturn(thanos7);
        thanos8.setDepartment("Department");
        thanos8.setEmail("jane.doe@example.org");
        thanos8.setFirstName("Jane");
        thanos8.setId(1);
        thanos8.setLastName("Doe");
        thanos8.setPassword("iloveyou");

        Thanos thanos9 = new Thanos();
        thanos9.setDepartment("Department");
        thanos9.setEmail("jane.doe@example.org");
        thanos9.setFirstName("Jane");
        thanos9.setId(1);
        thanos9.setLastName("Doe");
        thanos9.setPassword("iloveyou");

        Thanos thanos10 = new Thanos();
        thanos10.setDepartment("Department");
        thanos10.setEmail("jane.doe@example.org");
        thanos10.setFirstName("Jane");
        thanos10.setId(1);
        thanos10.setLastName("Doe");
        thanos10.setPassword("iloveyou");

        Thanos thanos11 = new Thanos();
        thanos11.setDepartment("Department");
        thanos11.setEmail("jane.doe@example.org");
        thanos11.setFirstName("Jane");
        thanos11.setId(1);
        thanos11.setLastName("Doe");
        thanos11.setPassword("iloveyou");
        Thanos thanos12 = mock(Thanos.class);
        when(thanos12.setDepartment((String) any())).thenReturn(thanos);
        when(thanos12.setEmail((String) any())).thenReturn(thanos1);
        when(thanos12.setFirstName((String) any())).thenReturn(thanos8);
        when(thanos12.setId(anyInt())).thenReturn(thanos9);
        when(thanos12.setLastName((String) any())).thenReturn(thanos10);
        when(thanos12.setPassword((String) any())).thenReturn(thanos11);
        thanos12.setDepartment("Department");
        thanos12.setEmail("jane.doe@example.org");
        thanos12.setFirstName("Jane");
        thanos12.setId(1);
        thanos12.setLastName("Doe");
        thanos12.setPassword("iloveyou");
        Optional<Thanos> ofResult = Optional.of(thanos12);

        Thanos thanos13 = new Thanos();
        thanos13.setDepartment("Department");
        thanos13.setEmail("jane.doe@example.org");
        thanos13.setFirstName("Jane");
        thanos13.setId(1);
        thanos13.setLastName("Doe");
        thanos13.setPassword("iloveyou");
        when(this.thanosRepository.save((Thanos) any())).thenReturn(thanos13);
        when(this.thanosRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(thanos13,
                this.thanosService.updateMember(new ThanosRequest("Jane", "Doe", "Department", "iloveyou"), 1));
        verify(this.thanosRepository).save((Thanos) any());
        verify(this.thanosRepository).findById((Integer) any());
        verify(thanos12).setDepartment((String) any());
        verify(thanos12).setEmail((String) any());
        verify(thanos12, atLeast(1)).setFirstName((String) any());
        verify(thanos12).setId(anyInt());
        verify(thanos12).setLastName((String) any());
        verify(thanos12).setPassword((String) any());
        verify(thanos8).setDepartment((String) any());
        verify(thanos8).setEmail((String) any());
        verify(thanos8).setFirstName((String) any());
        verify(thanos8).setId(anyInt());
        verify(thanos8, atLeast(1)).setLastName((String) any());
        verify(thanos8).setPassword((String) any());
    }

    /**
     * Method under test: {@link ThanosService#getPagingMembers(int, int, String)}
     */
    @Test
    void testGetPagingMembers() {
        when(this.thanosRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        assertNull(this.thanosService.getPagingMembers(1, 3, "Sort"));
        verify(this.thanosRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    /**
     * Method under test: {@link ThanosService#getPagingMembers(int, int, String)}
     */
    @Test
    void testGetPagingMembers2() {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");

        ArrayList<Thanos> thanosList = new ArrayList<>();
        thanosList.add(thanos);
        PageImpl<Thanos> pageImpl = new PageImpl<>(thanosList);
        when(this.thanosRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        assertEquals(1, this.thanosService.getPagingMembers(1, 3, "Sort").size());
        verify(this.thanosRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    /**
     * Method under test: {@link ThanosService#getPagingMembers(int, int, String)}
    @Test
    @Disabled("TODO: Complete this test")
    void testGetPagingMembers3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at demo.service.ThanosService.getPagingMembers(ThanosService.java:76)
        //   In order to prevent getPagingMembers(int, int, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   getPagingMembers(int, int, String).
        //   See https://diff.blue/R013 to resolve this issue.

        when(this.thanosRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(null);
        this.thanosService.getPagingMembers(1, 3, "Sort");
    }*/

    /**
     * Method under test: {@link ThanosService#getPagingMembers(int, int, String)}
    @Test
    @Disabled("TODO: Complete this test")
    void testGetPagingMembers4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Page index must not be less than zero!
        //       at org.springframework.data.domain.AbstractPageRequest.<init>(AbstractPageRequest.java:44)
        //       at org.springframework.data.domain.PageRequest.<init>(PageRequest.java:45)
        //       at org.springframework.data.domain.PageRequest.of(PageRequest.java:72)
        //       at demo.service.ThanosService.getPagingMembers(ThanosService.java:73)
        //   In order to prevent getPagingMembers(int, int, String)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   getPagingMembers(int, int, String).
        //   See https://diff.blue/R013 to resolve this issue.

        when(this.thanosRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        this.thanosService.getPagingMembers(-1, 3, "Sort");
    }*/

    /**
     * Method under test: {@link ThanosService#getPagingMembers(int, int, String)}
    @Test
    @Disabled("TODO: Complete this test")
    void testGetPagingMembers5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Property must not null or empty!
        //       at org.springframework.data.domain.Sort$Order.<init>(Sort.java:457)
        //       at org.springframework.data.domain.Sort$Order.<init>(Sort.java:396)
        //       at org.springframework.data.domain.Sort.lambda$new$0(Sort.java:71)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:195)
        //       at java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
        //       at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:913)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:578)
        //       at org.springframework.data.domain.Sort.<init>(Sort.java:72)
        //       at org.springframework.data.domain.Sort.by(Sort.java:87)
        //       at demo.service.ThanosService.getPagingMembers(ThanosService.java:73)
        //   In order to prevent getPagingMembers(int, int, String)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   getPagingMembers(int, int, String).
        //   See https://diff.blue/R013 to resolve this issue.

        when(this.thanosRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        this.thanosService.getPagingMembers(1, 3, "");
    }*/

    /**
     * Method under test: {@link ThanosService#getPagingMembers(int, int, String)}
     */
    @Test
    void testGetPagingMembers6() {
        when(this.thanosRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> this.thanosService.getPagingMembers(1, 3, "Sort"));
        verify(this.thanosRepository).findAll((org.springframework.data.domain.Pageable) any());
    }
}

