package com.xlf.server.web.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.xlf.server.mapper.SysParameterMapper;
import com.xlf.server.web.ParameterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.xlf.common.po.SysParameterPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.MyBeanUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.SysParameterVo;

/**
 * 参数设置业务层实现类
 *
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
@Service
public class ParameterServiceImpl implements ParameterService {
    @Resource
    private SysParameterMapper parameterMapper;

    @Override
    public List<SysParameterVo> findAll(Paging paging) throws Exception {
        RowBounds rwoBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        return parameterMapper.findAll(rwoBounds);
    }

    @Override
    public void add(SysParameterVo parameterVo) throws Exception {
        //将vo转换成Po
        SysParameterPo parameterPo = MyBeanUtils.copyProperties(parameterVo, SysParameterPo.class);
        parameterPo.setCreateTime(new Date());
        parameterPo.setUpdateTime(new Date());
        parameterPo.setId(ToolUtils.getUUID());
        //调用dao保存数据
        parameterMapper.insert(parameterPo);
    }

    @Override
    public void update(SysParameterVo parameterVo) throws Exception {
        //将vo转换成Po
        SysParameterPo parameterPo = MyBeanUtils.copyProperties(parameterVo, SysParameterPo.class);
        parameterPo.setUpdateTime(new Date());
        
        //调用dao修改数据
        parameterMapper.updateByPrimaryKey(parameterPo);
    }

    @Override
    public void delete(SysParameterVo parameterVo) {
        //调用dao删除数据
        parameterMapper.deleteByPrimaryKey(parameterVo.getId());
    }

    @Override
    public long findCount() {
        return parameterMapper.findCount();
    }

    @Override
    public String findParameter(String paraName) {
        return parameterMapper.findParameter(paraName);
    }
    
	@Override
	public List<String> findGrouptype() throws Exception {
		// TODO Auto-generated method stub
		return parameterMapper.findGrouptype();
	}

	@Override
	public List<SysParameterVo> getInfoByTime(SysParameterVo parameterVo, Paging paging) {
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return parameterMapper.getInfoByTime(parameterVo, startRow, pageSize);
	}

    @Override
    public Integer updateParameterByName(String paraName,String value) {
        return parameterMapper.updateParameterByName(paraName, value);
    }
}
