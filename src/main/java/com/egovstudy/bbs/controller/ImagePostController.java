package com.egovstudy.bbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.egovstudy.bbs.service.ImagePostService;
import com.egovstudy.bbs.service.ImageService;
import com.egovstudy.bbs.vo.Image;
import com.egovstudy.bbs.vo.ImagePost;
import com.egovstudy.bbs.vo.Member;

@Controller
public class ImagePostController {
	Logger logger = LogManager.getLogger();

	@Resource(name = "imagePostService")
	ImagePostService imagePostService;
	
	@Resource(name="imageService")
	ImageService imageService;
	
	@RequestMapping("/bbs/imageList.do")
	public ModelAndView imageList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bbs/imageList");
		//게시물 리스트
		List<ImagePost> postList = imagePostService.readAll();
		
		mav.addObject("posts", postList);
		return mav;
	}
	
	@RequestMapping("/bbs/imagePost/{id}.do")
	public ModelAndView imagePost(@PathVariable int id){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bbs/imagePost");
		ImagePost post = imagePostService.readById(id);
		List<Image> images = imageService.readByPost(id);
		
		mav.addObject("post", post);
		mav.addObject("images", images);
		
		return mav;
	}

	@RequestMapping("/bbs/imageWrite.do")
	public String write() {
		return "bbs/imageWrite";
	}

	@RequestMapping(value = "/bbs/imageWriteExec.do", method = RequestMethod.POST)
	public String imageUpload(MultipartHttpServletRequest req) {
		String returnUrl = "";
		String uploadPath = "C:\\egovbbs\\uploads";

		HttpSession session = req.getSession();
		List<MultipartFile> files = req.getFiles("files");

		// 세션에서 유저 정보 가져오기
		Member member = (Member) session.getAttribute("member");

		// 로그인 상태가 아니면 게시판으로 리다이렉트
		if (member == null) {
			returnUrl = "redirect:/bbs/imageList.do";
		} else {
			try {
				// DB에 게시물 저장
				ImagePost post = new ImagePost();
				
				post.setWriter(member.getId());
				post.setTitle(req.getParameter("title"));
				post.setContent(req.getParameter("content"));
				imagePostService.write(post);
				
				//파일 처리
				boolean isFirstImage = true;
				int seq = 1;
				for (MultipartFile file : files) {
					// 파일 이름 UUID 이용해 수정
					UUID uuid = UUID.randomUUID();
					
					File uploadFile = new File(uploadPath, uuid.toString() + "_" + file.getOriginalFilename());
					
					
					// 업로드 폴더가 없을 경우 생성
					if (!uploadFile.exists()) {
						uploadFile.mkdirs();
					}
					//업로드 전 확장자 체크. jpg, png 파일을 제외한 나머지는 업로드 X
					String extension = FilenameUtils.getExtension(uploadFile.getAbsolutePath());
					
					if(extension.equals("jpg") || extension.equals("png")){
						// 파일 업로드
						file.transferTo(uploadFile);
						
						// DB에 이미지 경로 저장
						logger.debug(post.getId());
						Image image = new Image();
						image.setPost(post.getId());
						image.setPath(uploadFile.getAbsolutePath());
						image.setSeq(seq);
						image.setFileName(uploadFile.getName());
						imageService.upload(image);
						seq++;
						
						//첨부파일 중 첫번째 이미지 파일이면 썸네일 생성
						if(isFirstImage){
							File thumbnail = new File(uploadPath, "thumb_" + uploadFile.getName());
							Thumbnails.of(uploadFile).size(450,200).outputFormat(extension).toFile(thumbnail);
							//썸네일 생성 후 게시물에 썸네일 경로 추가
							post.setThumbnail(thumbnail.getAbsolutePath());
							imagePostService.updateThumbnailPath(post);
							isFirstImage = false;
						}
					}
					
					returnUrl = "redirect:/bbs/imageList.do";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnUrl;
	}
	
	@RequestMapping(value="/bbs/thumbnail/{id}.do", method=RequestMethod.GET)
	@ResponseBody
	public byte[] getThumbnail(@PathVariable int id){
		ImagePost post = imagePostService.readById(id);
		
		String thumbnailPath = post.getThumbnail();
		
		File thumbnail = new File(thumbnailPath);
		
		byte[] thumbnailByte = new byte[(int) thumbnail.length()];
		FileInputStream fis;
		
		try {
			fis = new FileInputStream(thumbnail);
			fis.read(thumbnailByte);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return thumbnailByte;
	}
	
	@RequestMapping(value="/bbs/postImage/{post}/{seq}.do", method=RequestMethod.GET, produces=MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] getPostImage(@PathVariable int post, @PathVariable int seq){
		Image image = new Image();
		image.setPost(post);
		image.setSeq(seq);
		image = imageService.readByPostAndSeq(image);
		
		File imageFile = new File(image.getPath());
		
		byte[] imageByte = new byte[(int) imageFile.length()];
		FileInputStream fis;
		
		try{
			fis = new FileInputStream(imageFile);
			fis.read(imageByte);
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return imageByte;
	}
	
	@RequestMapping(value="/bbs/postImage/{post}.do", method=RequestMethod.GET)
	@ResponseBody
	public Map<Integer, byte[]> getImages(@PathVariable int post){
		List<Image> images = imageService.readByPost(post);
		Map<Integer, byte[]> result = new HashMap<Integer, byte[]>();
		int i = 0;
		
		for(Image img : images){
			File imgFile = new File(img.getPath());
			FileInputStream fis;
			byte[] imgByte = new byte[(int) imgFile.length()];
			
			try{
				fis = new FileInputStream(imgFile);
				fis.read(imgByte);
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			result.put(i, imgByte);
			i++;
		}
		
		return result;
	}
	
	@RequestMapping("/bbs/imagePostDelete/{id}.do")
	public String imagePostDelete(@PathVariable int id){
		if(deleteFiles(id)){
			imagePostService.remove(id);
		}
		return "redirect:/bbs/imageList.do";
	}
	
	private boolean deleteFiles(int id){
		boolean deleteResult = true;
		List<File> files = getFiles(id);
		
		for(File f : files){
			if(!f.delete()){
				deleteResult = false;
			}
		}
		
		imageService.removeByPostId(id);
		
		return deleteResult;
	}
	
	private List<File> getFiles(int id){
		List<File> files = new LinkedList<File>();
		
		if(!(imagePostService.readById(id).getThumbnail() == null)){
			files.add(new File(imagePostService.readById(id).getThumbnail()));
		
			for(Image img : imageService.readByPost(id)){
				String path = img.getPath();
				files.add(new File(path));
			}
		}
		
		return files;
	}
	
	@RequestMapping("/bbs/imagePostModify/{id}.do")
	public ModelAndView imagePostModify(@PathVariable int id){
		ModelAndView mav = new ModelAndView();
		ImagePost post = imagePostService.readById(id);
		mav.addObject("post", post);
		mav.setViewName("bbs/imageModify");
		return mav;
	}
	
	@RequestMapping(value = "/bbs/imagePostModifyExec.do", method=RequestMethod.POST)
	public ModelAndView imagePostModifyExec(MultipartHttpServletRequest req){
		ModelAndView mav = new ModelAndView();
		
		List<MultipartFile> files = req.getFiles("files");
		System.out.println(files);
		String uploadPath = "C:\\egovbbs\\uploads";
		//게시물 내용 수정
		ImagePost post = new ImagePost();
		post.setId(Integer.parseInt(req.getParameter("id")));
		post.setTitle(req.getParameter("title"));
		post.setContent(req.getParameter("content"));
		imagePostService.update(post);
		
		//이미지 내용 수정
		//기존에 저장된 이미지 삭제 후 새 이미지로 저장
		System.out.println("Post ID : " + req.getParameter("id"));
		if(deleteFiles(Integer.parseInt(req.getParameter("id")))){
			
			boolean isFirstImage = true;
			int seq = 1;
			try{
				for (MultipartFile file : files) {
					// 파일 이름 UUID 이용해 수정
					UUID uuid = UUID.randomUUID();
				
					File uploadFile = new File(uploadPath, uuid.toString() + "_" + file.getOriginalFilename());
				
				
					// 업로드 폴더가 없을 경우 생성
					if (!uploadFile.exists()) {
						uploadFile.mkdirs();
					}
					//업로드 전 확장자 체크. jpg, png 파일을 제외한 나머지는 업로드 X
					String extension = FilenameUtils.getExtension(uploadFile.getAbsolutePath());
				
					if(extension.equals("jpg") || extension.equals("png")){
						// 파일 업로드
						file.transferTo(uploadFile);
					
						// DB에 이미지 경로 저장
						logger.debug(post.getId());
						Image image = new Image();
						image.setPost(post.getId());
						image.setPath(uploadFile.getAbsolutePath());
						image.setSeq(seq);
						image.setFileName(uploadFile.getName());
						imageService.upload(image);
						seq++;
					
						//첨부파일 중 첫번째 이미지 파일이면 썸네일 생성
						if(isFirstImage){
							File thumbnail = new File(uploadPath, "thumb_" + uploadFile.getName());
							Thumbnails.of(uploadFile).size(450,200).outputFormat(extension).toFile(thumbnail);
							//썸네일 생성 후 게시물에 썸네일 경로 추가
							post.setThumbnail(thumbnail.getAbsolutePath());
							imagePostService.updateThumbnailPath(post);
							isFirstImage = false;
						}	
					}
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		mav.setViewName("redirect:/");
		return mav;
	}
}
