import { useContext, useState } from "react";
import {
    Button,
    FormControl,
    FormLabel,
    Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay,
    Select,
    Text,
    useToast,
} from "@chakra-ui/react";

import * as channelService from "../../../../services/channelService";
import AuthContext from "../../../../contexts/authContext";
import { useParams } from "react-router-dom";

export default function ChannelAddUser({
    isOpen,
    onClose,
    fetchChannel,
    isOwner,
}) {
    const [values, setValues] = useState({
        email: "",
        role: "",
    });
    const [errors, setErrors] = useState({
        email: "",
        role: "",
    });
    const { id: userId } = useContext(AuthContext);
    const { channelId } = useParams();
    const toast = useToast();
    const roles = ["ADMIN", "GUEST"];

    const onChange = (e) => {
        setValues((state) => ({
            ...state,
            [e.target.name]: e.target.value,
        }));
    };

    const validateForm = (newMember) => {
        const newErrors = {
            email: "",
            role: "",
        };
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!newMember.email.trim()) {
            newErrors.email = "Email can't be empty";
        } else if (!emailRegex.test(newMember.email)) {
            newErrors.email = "Invalid email";
        }
        if (!newMember.role.trim()) {
            newErrors.role = "Role can't be empty";
        } else if (!roles.includes(newMember.role)) {
            newErrors.role = "Invalid role";
        }
        setErrors(newErrors);

        return !newErrors.email && !newErrors.role;
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        setErrors({
            email: "",
            role: "",
        });
        const newMember = {
            email: values.email,
            role: values.role,
        };
        if (!validateForm(newMember)) {
            return;
        }

        try {
            await channelService.addMember(channelId, userId, newMember);
            toast({
                title: "Member added successfully!",
                status: "success",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
            fetchChannel();
            onCloseForm();
        } catch (error) {
            toast({
                title: error.message || "Member could not be added!",
                status: "error",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
        }
    };

    const onCloseForm = () => {
        onClose();
    };

    return (
        <Modal isOpen={isOpen} onClose={onCloseForm}>
            <ModalOverlay />
            <ModalContent mx={{ base: "4", md: "0" }}>
                <ModalHeader>Add member</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                    <form onSubmit={onSubmit}>
                        <FormControl mb={4} isRequired>
                            <FormLabel>Email</FormLabel>
                            <Input
                                type="text"
                                name="email"
                                value={values.email}
                                onChange={onChange}
                                placeholder="Email"
                            />
                            {errors.email && (
                                <Text color="red.500" fontSize="sm">
                                    {errors.email}
                                </Text>
                            )}
                        </FormControl>
                        <FormControl mb={4} isRequired>
                            <FormLabel>Role</FormLabel>
                            <Select
                                name="role"
                                value={values.role}
                                onChange={onChange}
                                placeholder="Select role"
                            >
                                {isOwner && (
                                    <option key="ADMIN" value="ADMIN">
                                        Admin
                                    </option>
                                )}
                                <option key="GUEST" value="GUEST">
                                    Guest
                                </option>
                            </Select>
                            {errors.role && (
                                <Text color="red.500" fontSize="sm">
                                    {errors.role}
                                </Text>
                            )}
                        </FormControl>
                    </form>
                </ModalBody>
                <ModalFooter>
                    <Button variant="primary" mr={3} onClick={onSubmit}>
                        Add
                    </Button>
                    <Button onClick={onCloseForm}>Cancel</Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
}
