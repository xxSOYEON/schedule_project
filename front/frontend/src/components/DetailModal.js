import React, { useEffect, useState } from "react";
import { Modal, Form, Input, DatePicker, Select, Button, TimePicker } from "antd";
import dayjs from "dayjs";

const { Option } = Select;

const DetailModal = ({ event, visible, onClose, onSave, onDelete }) => {
  const [form] = Form.useForm();
  const [isSaveDisabled, setIsSaveDisabled] = useState(true); // Save 버튼 활성화/비활성화 상태

  // 필수값 검증
  const validateFields = () => {
    const values = form.getFieldsValue();
    const isValid = values.title && values.startDate && values.endDate;
    setIsSaveDisabled(!isValid);
  };

  useEffect(() => {
    if (!visible) return; // 모달이 닫혀있으면 실행하지 않음

    form.resetFields();

    // ✅ event가 있을 때만 값 설정
    form.setFieldsValue({
      title: event?.title || "",
      description: event?.description || "",
      startDate: event?.startDate ? dayjs(event.startDate) : null,
      endDate: event?.endDate ? dayjs(event.endDate) : null,
      startTime: event?.startTime ? dayjs(event.startTime, "HH:mm") : dayjs("00:00", "HH:mm"),
      endTime: event?.endTime ? dayjs(event.endTime, "HH:mm") : dayjs("00:00", "HH:mm"),
      priorityName: event?.priorityName || "All",
    });

    validateFields(); // ✅ 필수 필드 검증
  }, [event, visible]);

  // 값 변경 시 필수 입력값 검사
  const handleValuesChange = () => {
    validateFields();
  };

  const handleSave = () => {
    form.validateFields().then((values) => {
      const newEvent = {
        id: event ? event.id : null,
        title: values.title,
        description: values.description,
        startDate: values.startDate ? values.startDate.format("YYYY-MM-DD") : null,
        endDate: values.endDate ? values.endDate.format("YYYY-MM-DD") : null,
        startTime: values.startTime ? values.startTime.format("HH:mm") : "00:00",
        endTime: values.endTime ? values.endTime.format("HH:mm") : "00:00",
        priorityName: values.priorityName,
      };
      onSave(newEvent);
    });
  };

  const handleDelete = () => {
    if (event) {
      onDelete(event.id);
      form.resetFields();
    }
  };

  return (
    <Modal
      title={event ? "일정 수정" : "새 일정 추가"}
      open={visible}
      onCancel={onClose}
      footer={[
        <Button key="cancel" onClick={onClose}>
          취소
        </Button>,
        event && (
          <Button key="delete" type="default" danger onClick={handleDelete}>
            삭제
          </Button>
        ),
        <Button key="submit" type="primary" onClick={handleSave} disabled={isSaveDisabled}>
          {event ? "수정" : "추가"}
        </Button>,
      ]}
    >
      <Form
        form={form}
        layout="vertical"
        onValuesChange={handleValuesChange}
        initialValues={{
          title: event?.title || "",
          description: event?.description || "",
          startDate: event?.startDate ? dayjs(event.startDate) : null,
          endDate: event?.endDate ? dayjs(event.endDate) : null,
          startTime: event?.startTime ? dayjs(event.startTime, "HH:mm") : dayjs("00:00", "HH:mm"),
          endTime: event?.endTime ? dayjs(event.endTime, "HH:mm") : dayjs("00:00", "HH:mm"),
          priorityName: event?.priorityName || "All",
        }}
      >
        <Form.Item name="title" label="제목" rules={[{ required: true, message: "제목을 입력하세요!" }]}>
          <Input />
        </Form.Item>
        <Form.Item name="description" label="설명">
          <Input.TextArea />
        </Form.Item>
        <Form.Item name="startDate" label="시작 날짜" rules={[{ required: true, message: "시작 날짜를 선택하세요!" }]}>
          <DatePicker />
        </Form.Item>
        <Form.Item name="endDate" label="종료 날짜" rules={[{ required: true, message: "종료 날짜를 선택하세요!" }]}>
          <DatePicker />
        </Form.Item>
        <Form.Item name="startTime" label="시작 시간">
          <TimePicker format="HH:mm" minuteStep={30} />
        </Form.Item>
        <Form.Item name="endTime" label="종료 시간">
          <TimePicker format="HH:mm" minuteStep={30} />
        </Form.Item>
        <Form.Item name="priorityName" label="중요도">
          <Select>
            <Option value="High">High</Option>
            <Option value="Middle">Middle</Option>
            <Option value="Low">Low</Option>
          </Select>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default DetailModal;
