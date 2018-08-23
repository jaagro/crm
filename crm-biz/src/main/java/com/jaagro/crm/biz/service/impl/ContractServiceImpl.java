package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.contract.*;
import com.jaagro.crm.api.dto.response.contract.ReturnContractDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractPriceDto;
import com.jaagro.crm.api.service.ContractPriceService;
import com.jaagro.crm.api.service.ContractService;
import com.jaagro.crm.biz.entity.Contract;
import com.jaagro.crm.biz.entity.ContractPrice;
import com.jaagro.crm.biz.entity.ContractSectionPrice;
import com.jaagro.crm.biz.mapper.ContractLogMapper;
import com.jaagro.crm.biz.mapper.ContractMapper;
import com.jaagro.crm.biz.mapper.ContractPriceMapper;
import com.jaagro.crm.biz.mapper.ContractSectionPriceMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import utils.ResponseStatusCode;
import utils.ServiceResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 */
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractPriceMapper contractPriceMapper;
    @Autowired
    private ContractSectionPriceMapper contractSectionPriceMapper;
    @Autowired
    private ContractLogMapper contractLogMapper;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private ContractPriceService priceService;

    /**
     * 创建合同
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createContract(CreateContractDto dto) {
        //创建contract对象
        Contract contract = new Contract();
        BeanUtils.copyProperties(dto, contract);
        contract
                .setContractStatus(1)
                .setCreateTime(new Date())
                .setCreateUser(userService.getCurrentUser().getId());
        contractMapper.insert(contract);

        /**
         * 未完成--创建资质证(表未确认)
         */

        //创建contractPrice对象
        createPrice(dto, contract);
        return ServiceResult.toResult("合同创建成功");
    }

    @Override
    public Map<String, Object> createContract(List<CreateContractDto> dtos, Integer CustomerId) {
        if (dtos != null && dtos.size() > 0) {
            for (CreateContractDto contractDto : dtos) {
                //创建contract对象
                Contract contract = new Contract();
                BeanUtils.copyProperties(contractDto, contract);
                contract
                        .setCustomerId(CustomerId)
                        .setCreateTime(new Date())
                        .setCreateUser(userService.getCurrentUser().getId());
                contractMapper.insert(contract);

                //创建contractPrice对象
                createPrice(contractDto, contract);
            }
        }
        return ServiceResult.toResult("合同列表创建成功");
    }

    /**
     * 修改合同
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateContract(UpdateContractDto dto) {
        // 创建contract对象
        Contract contract = new Contract();
        BeanUtils.copyProperties(dto, contract);
        contract
                .setNewUpdateTime(new Date())
                .setNewUpdateUser(userService.getCurrentUser().getId());
        contractMapper.updateByPrimaryKeySelective(contract);

        //删除原数据
        List<ReturnContractPriceDto> priceList = contractPriceMapper.listByContractId(dto.getId());
        if (priceList.size() > 0) {
            for (ReturnContractPriceDto cp : priceList) {
                contractSectionPriceMapper.deleteByPriceId(cp.getId());
            }
            contractPriceMapper.deleteByContractId(dto.getId());
        }
        //创建contractPrice对象
        createPrice(dto, contract);
        return ServiceResult.toResult("合同修改成功");
    }

    @Override
    public Map<String, Object> updateContract(List<UpdateContractDto> dtos) {
        if (dtos != null && dtos.size() > 0) {
            for (UpdateContractDto contractDto : dtos) {
                // 创建contract对象
                Contract contract = new Contract();
                BeanUtils.copyProperties(contractDto, contract);
                contract
                        .setNewUpdateTime(new Date())
                        .setNewUpdateUser(userService.getCurrentUser().getId());
                contractMapper.updateByPrimaryKeySelective(contract);

                //删除原数据
                List<ReturnContractPriceDto> priceList = contractPriceMapper.listByContractId(contractDto.getId());
                if (priceList.size() > 0) {
                    for (ReturnContractPriceDto cp : priceList) {
                        contractSectionPriceMapper.deleteByPriceId(cp.getId());
                    }
                    contractPriceMapper.deleteByContractId(contractDto.getId());
                }
                //创建contractPrice对象
                createPrice(contractDto, contract);
            }
        }
        return ServiceResult.toResult("合同修改成功");
    }

    public void createPrice(UpdateContractDto dto, Contract contract) {
        //创建contractPrice对象
        if (dto.getPrice() != null && dto.getPrice().size() > 0) {
            for (UpdateContractPriceDto cp : dto.getPrice()) {
                ContractPrice contractPrice = new ContractPrice();
                BeanUtils.copyProperties(cp, contractPrice);
                contractPrice
                        .setContractId(contract.getId())
                        .setPriceStatus(1);
                if (StringUtils.isEmpty(contractPrice.getPricingType())) {
                    throw new RuntimeException("计价模式不能为空");
                }
                contractPriceMapper.insert(contractPrice);
                //创建contractSectionPrice对象
                if (cp.getSectionPrice() != null && cp.getSectionPrice().size() > 0) {
                    for (CreateContractSectionPriceDto cspDto : cp.getSectionPrice()) {
                        ContractSectionPrice csp = new ContractSectionPrice();
                        BeanUtils.copyProperties(cspDto, csp);
                        csp
                                .setContractPriceId(contractPrice.getId())
                                .setSelectionStatus(1);
                        contractSectionPriceMapper.insert(csp);
                    }
                }
            }
        }
    }

    public void createPrice(CreateContractDto dto, Contract contract) {
        //创建contractPrice对象
        if (dto.getPrice() != null && dto.getPrice().size() > 0) {
            for (CreateContractPriceDto cp : dto.getPrice()) {
                ContractPrice contractPrice = new ContractPrice();
                BeanUtils.copyProperties(cp, contractPrice);
                contractPrice
                        .setContractId(contract.getId())
                        .setPriceStatus(1);
                if (StringUtils.isEmpty(contractPrice.getPricingType())) {
                    throw new RuntimeException("计价模式不能为空");
                }
                contractPriceMapper.insert(contractPrice);
                //创建contractSectionPrice对象
                if (cp.getSectionPrice() != null && cp.getSectionPrice().size() > 0) {
                    for (CreateContractSectionPriceDto cspDto : cp.getSectionPrice()) {
                        ContractSectionPrice csp = new ContractSectionPrice();
                        BeanUtils.copyProperties(cspDto, csp);
                        csp
                                .setContractPriceId(contractPrice.getId())
                                .setSelectionStatus(1);
                        contractSectionPriceMapper.insert(csp);
                    }
                }
            }
        }
    }

    /**
     * 查询单个合同
     *
     * @param contractId
     * @return
     */
    @Override
    public Map<String, Object> getById(Integer contractId) {
        if (contractMapper.selectByPrimaryKey(contractId) == null) {
            return ServiceResult.error(ResponseStatusCode.ID_VALUE_ERROR.getCode(), "id: " + contractId + "不存在");
        }
        return ServiceResult.toResult(contractMapper.getById(contractId));
    }

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> listByCriteria(ListContractCriteriaDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<ReturnContractDto> contracts = contractMapper.listByPage(dto);
        return ServiceResult.toResult(new PageInfo<>(contracts));
    }

    @Override
    public Map<String, Object> disableById(Integer id) {
        ReturnContractDto contractDto = this.contractMapper.getById(id);
        Contract contract = new Contract();
        BeanUtils.copyProperties(contractDto, contract);
        contract.setContractStatus(0);
        this.contractMapper.updateByPrimaryKeySelective(contract);
        if (contractDto.getPrices() != null && contractDto.getPrices().size() > 0) {
            this.priceService.disableByContractId(contract.getId());
        }
        return ServiceResult.toResult("合同删除成功");
    }

    @Override
    public Map<String, Object> disableByID(List<ReturnContractDto> dtos) {
        for (ReturnContractDto returnContractDto : dtos
        ) {
            ReturnContractDto contractDto = this.contractMapper.getById(returnContractDto.getId());
            Contract contract = new Contract();
            BeanUtils.copyProperties(contractDto, contract);
            contract.setContractStatus(0);
            this.contractMapper.updateByPrimaryKeySelective(contract);
            if (contractDto.getPrices() != null && contractDto.getPrices().size() > 0) {
                this.priceService.disableByContractId(contract.getId());
            }
        }
        return ServiceResult.toResult("合同删除成功");
    }

}
