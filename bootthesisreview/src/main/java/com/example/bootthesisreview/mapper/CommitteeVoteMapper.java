package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.Committee;
import com.example.bootthesisreview.entity.CommitteeVoteVO;
import com.example.bootthesisreview.entity.Committeevote;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommitteeVoteMapper extends BaseMapper<Committeevote> {
    @Select("SELECT thesis.id,\n" +
            "thesis.thesisid,\n" +
            "thesis.title,\n" +
            "thesis.department,\n" +
            "committeevote.committeeid,\n" +
            "committeevote.`comment`,\n" +
            "committeevote.voters,\n" +
            "committeevote.noes,\n" +
            "committeevote.yesses,\n" +
            "thesis.status, \n" +
            "(case when committee.after_defence=1 then \"内审\" else \"答辩\" end) as process, \n" +
            "(CASE WHEN committeevote.noes/committeevote.yesses<1/2 THEN '通过' ELSE '不通过' END) as results \n" +
            "from thesis JOIN committeevote on thesis.thesisid=committeevote.thesisid join committee on committeevote.committeeid=committee.id \n" +
            "where (thesis.`status`=4 or thesis.`status`=5 or thesis.`status`=6 or thesis.`status`=9 or thesis.`status`=10 or thesis.`status`=11) and thesis.batch=#{batch};")
    List<CommitteeVoteVO> committeevoteListForBatch(String batch);
    @Select("SELECT thesis.id,\n" +
            "thesis.thesisid,\n" +
            "thesis.title,\n" +
            "thesis.department,\n" +
            "committeevote.committeeid,\n" +
            "committeevote.`comment`,\n" +
            "committeevote.voters,\n" +
            "committeevote.noes,\n" +
            "committeevote.yesses,\n" +
            "thesis.status, \n" +
            "(case when committee.after_defence=1 then \"内审\" else \"答辩\" end) as process, \n" +
            "(CASE WHEN committeevote.noes/committeevote.yesses<1/2 THEN '通过' ELSE '不通过' END) as results \n" +
            "from thesis JOIN committeevote on thesis.thesisid=committeevote.thesisid join committee on committeevote.committeeid=committee.id \n" +
            "where thesis.`status`=4 or thesis.`status`=5 or thesis.`status`=6 or thesis.`status`=9 or thesis.`status`=10 or thesis.`status`=11;")
    List<CommitteeVoteVO> committeevoteList();

    @Select({"select t.thesisid,t.title,t.department,t.memo, c.yesses,c.noes,c.voters from committeeVote c, thesis t where c.thesisid =t.thesisid and c.thesisid=#{thesisid}"})
//    List<GradeVO> GradeList(Integer url) ;
    List<CommitteeVoteVO> committeeVoteList(String thesisid);


}
