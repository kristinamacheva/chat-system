import { useContext, useState } from "react";
import {
    Avatar,
    Button,
    FormControl,
    FormLabel,
    Heading,
    HStack,
    Icon,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay,
    Select,
    Stack,
    Text,
    useToast,
} from "@chakra-ui/react";

import * as channelService from "../../../../../../services/channelService";
import { useParams } from "react-router-dom";
import { FaEnvelope } from "react-icons/fa";
import AuthContext from "../../../../../../contexts/authContext";

export default function ChannelUpdateMember({
    isOpen,
    onClose,
    fetchMembers,
    member,
}) {
    const [values, setValues] = useState({
        role: member.role,
    });
    const [errors, setErrors] = useState({
        role: "",
    });
    const { id: currentUserId } = useContext(AuthContext);
    const { channelId } = useParams();
    const toast = useToast();
    const roles = ["ADMIN", "GUEST"];

    const onChange = (e) => {
        setValues((state) => ({
            ...state,
            [e.target.name]: e.target.value,
        }));
    };

    const validateForm = (updatedMember) => {
        const newErrors = {
            role: "",
        };
        if (!updatedMember.role.trim()) {
            newErrors.role = "Role can't be empty";
        } else if (!roles.includes(updatedMember.role)) {
            newErrors.role = "Invalid role";
        }
        setErrors(newErrors);

        return !newErrors.role;
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        setErrors({
            role: "",
        });
        const updatedMember = {
            role: values.role,
        };
        if (!validateForm(updatedMember)) {
            return;
        }

        try {
            await channelService.updateMember(channelId, currentUserId, member.id, updatedMember);
            toast({
                title: "Member updated successfully!",
                status: "success",
                duration: 6000,
                isClosable: true,
                position: "bottom",
            });
            fetchMembers();
            onCloseForm();
        } catch (error) {
            toast({
                title: error.message || "Member could not be updated!",
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
                <ModalHeader>Update member</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                    <Stack direction="row" spacing="5">
                        <Avatar name={member.fullName} />
                        <Stack spacing="0.5" alignItems="center">
                            <Heading as="h4" size="sm">
                                {member.fullName}
                            </Heading>
                            <HStack>
                                <Icon as={FaEnvelope} color="themePurple.800" />
                                <Text>{member.email}</Text>
                            </HStack>
                        </Stack>
                    </Stack>
                    <form onSubmit={onSubmit}>
                        <FormControl mb={4} isRequired>
                            <FormLabel>Role</FormLabel>
                            <Select
                                name="role"
                                value={values.role}
                                onChange={onChange}
                                placeholder="Select role"
                            >
                                <option key="ADMIN" value="ADMIN">
                                    Admin
                                </option>
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
                        Update
                    </Button>
                    <Button onClick={onCloseForm}>Cancel</Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
}
