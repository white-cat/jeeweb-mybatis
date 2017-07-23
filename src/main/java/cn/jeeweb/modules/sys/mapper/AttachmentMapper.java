package cn.jeeweb.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.jeeweb.modules.sys.entity.Attachment;

/**
 * 附件数据库控制层接口
 */
public interface AttachmentMapper extends BaseMapper<Attachment> {
	List<Attachment> selectAttachmentPage(Page<Attachment> page, @Param("ew") Wrapper<Attachment> wrapper);
}