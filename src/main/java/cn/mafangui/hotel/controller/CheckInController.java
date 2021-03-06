package cn.mafangui.hotel.controller;

import cn.mafangui.hotel.entity.CheckIn;
import cn.mafangui.hotel.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/checkIn")
public class CheckInController {

    private final CheckInService checkInService;

    @Autowired
    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    /**
     * 入住登记
     * @param peoCount
     * @param persons
     * @param ids
     * @return
     */
    @RequestMapping(value = "/in")
    public HashMap addCheckIn(int orderId, int peoCount, String persons, String ids){
        CheckIn checkIn = new CheckIn();
        checkIn.setOrderId(orderId);
        checkIn.setPeoCount(peoCount);
        checkIn.setPersons(persons);
        checkIn.setIds(ids);
        return checkInService.checkIn(checkIn);
    }

    /**
     * 退房登记
     *
     * @param roomNumber
     * @return
     */
    @RequestMapping(value = "/out")
    public int checkOut(String roomNumber) {
        try {
            return checkInService.checkOut(roomNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    @RequestMapping(value = "/delete")
    public int deleteCheckIn(int checkId){
        return checkInService.delete(checkId);
    }

    @RequestMapping(value = "/update")
    public int update(int checkId,String roomNumber){
        CheckIn checkIn = new CheckIn();
        checkIn.setCheckInId(checkId);
        checkIn.setRoomNumber(roomNumber);
        return checkInService.update(checkIn);
    }



    @RequestMapping(value = "/withId")
    public CheckIn getById(int checkId){
        return checkInService.selectById(checkId);
    }

    @RequestMapping(value = "/all")
    public List<CheckIn> getAll(){
        return checkInService.selectAll();
    }

}
